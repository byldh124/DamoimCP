package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.constant.RequestParam
import com.moondroid.damoim.common.exception.DMException
import com.moondroid.damoim.data.datasource.local.LocalDataSource
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.model.status.doInFlow
import com.moondroid.damoim.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ProfileRepository {
    override suspend fun getProfile(): Flow<ApiResult<Profile>> = doInFlow {
        val profileEntity = localDataSource.getProfile() ?: throw DMException.ProfileException()
        emit(ApiResult.Success(profileEntity.toProfile()))
    }

    override suspend fun updateToken(token: String): Flow<ApiResult<Unit>> = doInFlow {
        val id = localDataSource.getId()
        emit(remoteDataSource.updateToken(id, token))
    }


    override suspend fun updateInterest(interest: String): Flow<ApiResult<Unit>> = doInFlow {
        val id = localDataSource.getId()
        emit(remoteDataSource.updateInterest(id, interest))
    }

    override suspend fun deleteProfile(): Flow<ApiResult<Unit>> = doInFlow {
        emit(ApiResult.Success(localDataSource.deleteProfile()))
    }

    override suspend fun updateProfile(
        name: String,
        birth: String,
        gender: String,
        location: String,
        message: String,
        thumb: File?,
    ): Flow<ApiResult<Profile>> = doInFlow {
        val id = localDataSource.getId()
        val body = HashMap<String, RequestBody>()
        body[RequestParam.ID] = id.toRequestBody()
        body[RequestParam.NAME] = name.toRequestBody()
        body[RequestParam.BIRTH] = birth.toRequestBody()
        body[RequestParam.GENDER] = gender.toRequestBody()
        body[RequestParam.LOCATION] = location.toRequestBody()
        body[RequestParam.MESSAGE] = message.toRequestBody()

        var thumbPart: MultipartBody.Part? = null
        thumb?.let { file ->
            val requestBody = file.asRequestBody("image/*".toMediaType())
            thumbPart = MultipartBody.Part.createFormData("thumb", file.name, requestBody)
        }

        emit(remoteDataSource.updateProfile(body, thumbPart).convert { it.toProfile() })
    }
}
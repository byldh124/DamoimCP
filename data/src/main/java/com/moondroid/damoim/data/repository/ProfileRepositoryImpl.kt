package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao
) : ProfileRepository {
    override suspend fun getProfile(): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            localDataSource.getProfile().run {
                this?.let {
                    emit(ApiResult.Success(it.toProfile()))
                } ?: run {
                    emit(ApiResult.Error(IllegalStateException("Data is empty")))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateToken(id: String, token: String): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.updateToken(id, token))
    }.flowOn(Dispatchers.IO)


    override suspend fun updateInterest(interest: String): Flow<ApiResult<Unit>> {
        return flow<ApiResult<Unit>> {
            localDataSource.getProfile().run {
                this?.let {
                    remoteDataSource.updateInterest(it.id, interest).run {
                        when (this) {
                            is ApiResult.Error -> emit(ApiResult.Error(throwable))
                            is ApiResult.Fail -> emit(ApiResult.Fail(code))
                            is ApiResult.Success -> emit(ApiResult.Success(response))
                        }
                    }
                } ?: run {
                    emit(ApiResult.Error(IllegalStateException("Data is empty")))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteProfile(): Flow<ApiResult<Boolean>> {
        return flow {
            localDataSource.deleteProfileAll().run {
                emit(ApiResult.Success(true))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateProfile(
        id: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        message: String,
        thumb: File?,
    ): Flow<ApiResult<Profile>>  = flow {
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

        remoteDataSource.updateProfile(body, thumbPart).run {
            when (this) {
                is ApiResult.Success -> {
                    localDataSource.deleteProfileAll()
                    localDataSource.insertProfile(response)
                    emit(ApiResult.Success(response.toProfile()))
                }

                is ApiResult.Error -> emit(ApiResult.Error(throwable))
                is ApiResult.Fail -> emit(ApiResult.Fail(code))
            }
        }
    }
}
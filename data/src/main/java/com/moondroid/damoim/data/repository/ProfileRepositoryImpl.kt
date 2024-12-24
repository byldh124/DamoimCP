package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.common.constant.RequestParam
import com.moondroid.damoim.common.exception.DMException
import com.moondroid.damoim.data.datasource.local.LocalDataSource
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.mapper.DataMapper.toProfileEntity
import com.moondroid.damoim.data.model.request.UpdateTokenRequest
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.model.status.doInFlow
import com.moondroid.damoim.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ProfileRepository {
    override fun getProfile(): Flow<ApiResult<Profile>> = doInFlow {
        val profileEntity = localDataSource.getProfile() ?: throw DMException.ProfileException()
        emit(ApiResult.Success(profileEntity.toProfile()))
    }

    override fun updateToken(token: String): Flow<ApiResult<NoResult>> = doInFlow {
        val id = localDataSource.getId()
        emit(remoteDataSource.updateToken(UpdateTokenRequest(id, token)))
    }


    override fun updateInterest(interest: String): Flow<ApiResult<NoResult>> = doInFlow {
        val id = localDataSource.getId()
        emit(remoteDataSource.updateInterest(id, interest))
    }

    override fun deleteProfile(): Flow<ApiResult<Unit>> = doInFlow {
        emit(ApiResult.Success(localDataSource.deleteProfile()))
    }

    override fun updateProfile(
        name: String,
        birth: String,
        gender: String,
        location: String,
        message: String,
        thumb: File?,
    ): Flow<ApiResult<Profile>> = doInFlow {
        val id = localDataSource.getId()
        val body = HashMap<String, String>()
        body[RequestParam.ID] = id
        body[RequestParam.NAME] = name
        body[RequestParam.BIRTH] = birth
        body[RequestParam.GENDER] = gender
        body[RequestParam.LOCATION] = location
        body[RequestParam.MESSAGE] = message

        remoteDataSource.updateProfile(body, thumb).run {
            when (this) {
                is ApiResult.Success -> {
                    localDataSource.deleteProfile()
                    localDataSource.insertProfile(response.toProfileEntity())
                    emit(ApiResult.Success(response.toProfile()))
                }

                is ApiResult.SuccessWithoutResult -> emit(ApiResult.Error(DMException.NoResultException()))

                is ApiResult.Error -> emit(ApiResult.Error(throwable))
                is ApiResult.Fail -> emit(ApiResult.Fail(fail))
            }
        }
    }
}
package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.common.exception.DMException
import com.moondroid.damoim.data.datasource.local.LocalDataSource
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.mapper.DataMapper.toProfileEntity
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.model.status.doInFlow
import com.moondroid.damoim.domain.repository.SignRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : SignRepository {
    override suspend fun signUp(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String,
    ): Flow<ApiResult<Profile>> = doInFlow {
        remoteDataSource.signUp(
            SignUpRequest(id, hashPw, salt, name, birth, gender, location, interest, thumb)
        ).run {
            when (this) {
                is ApiResult.Success -> {
                    localDataSource.deleteProfile()
                    localDataSource.insertProfile(response.toProfileEntity())
                    emit(ApiResult.Success(response.toProfile()))
                }

                is ApiResult.Error -> emit(ApiResult.Error(throwable))
                is ApiResult.Fail -> emit(ApiResult.Fail(code))
                is ApiResult.SuccessWithoutResult -> emit(ApiResult.Error(DMException.NoResultException()))
            }
        }
    }

    override suspend fun signIn(id: String, hashPw: String): Flow<ApiResult<Profile>> = doInFlow {
        remoteDataSource.signIn(SignInRequest(id, hashPw)).run {
            when (this) {
                is ApiResult.Success -> {
                    localDataSource.deleteProfile()
                    localDataSource.insertProfile(response.toProfileEntity())
                    emit(ApiResult.Success(response.toProfile()))
                }
                is ApiResult.Error -> emit(ApiResult.Error(throwable))
                is ApiResult.Fail -> emit(ApiResult.Fail(code))
                is ApiResult.SuccessWithoutResult -> emit(ApiResult.Error(DMException.NoResultException()))
            }
        }
    }

    override suspend fun socialSign(id: String): Flow<ApiResult<Profile>> = doInFlow {
        remoteDataSource.socialSign(SocialSignRequest(id)).run {
            when (this) {
                is ApiResult.Success -> {
                    localDataSource.deleteProfile()
                    localDataSource.insertProfile(response.toProfileEntity())
                    emit(ApiResult.Success(response.toProfile()))
                }

                is ApiResult.Error -> emit(ApiResult.Error(throwable))
                is ApiResult.Fail -> emit(ApiResult.Fail(code))
                is ApiResult.SuccessWithoutResult -> emit(ApiResult.Error(DMException.NoResultException()))
            }
        }
    }

    override suspend fun resign(id: String): Flow<ApiResult<NoResult>> = doInFlow {
        emit(remoteDataSource.resign(id))
    }

    override suspend fun getSalt(id: String): Flow<ApiResult<String>> = doInFlow {
        emit(remoteDataSource.getSalt(SaltRequest(id)))
    }
}
package com.moondroid.damoim.data.repository

import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.SignRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao,
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
    ): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            remoteDataSource.signUp(
                SignUpRequest(id, hashPw, salt, name, birth, gender, location, interest, thumb)
            ).run {
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
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun signIn(id: String, hashPw: String): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            remoteDataSource.signIn(SignInRequest(id, hashPw)).run {
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
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun socialSign(id: String): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            remoteDataSource.socialSign(SocialSignRequest(id)).run {
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
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun resign(id: String): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.resign(id))
    }.flowOn(Dispatchers.IO)

    override suspend fun getSalt(id: String): Flow<ApiResult<String>> {
        return flow<ApiResult<String>> {
            remoteDataSource.getSalt(SaltRequest(id)).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}
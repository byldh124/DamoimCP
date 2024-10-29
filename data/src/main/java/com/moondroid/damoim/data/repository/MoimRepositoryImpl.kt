package com.moondroid.damoim.data.repository

import com.google.gson.Gson
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toMoimItem
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.MoimRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoimRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : MoimRepository {

    override suspend fun createMoim(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String
    ): Flow<ApiResult<Unit>> {
        return flow {
            val request =
                CreateMoimRequest(title, address, date, time, pay, lat, lng, Gson().toJson(listOf(joinMember)))
            emit(remoteDataSource.createMoim(request))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMoims(title: String): Flow<ApiResult<List<MoimItem>>> {
        return flow<ApiResult<List<MoimItem>>> {
            remoteDataSource.getMoims(title).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toMoimItem() }))
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getMoimMembers(joinMember: String): Flow<ApiResult<List<Profile>>> {
        return flow<ApiResult<List<Profile>>> {
            remoteDataSource.getMoimMember(joinMember).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toProfile() }))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun joinMoim(id: String, title: String, date: String): Flow<ApiResult<MoimItem>> {
        return flow<ApiResult<MoimItem>> {
            remoteDataSource.joinMoim(id, title, date).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.toMoimItem()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}
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
import kotlinx.coroutines.flow.catch
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
        }.catch {
            emit(ApiResult.Error(it))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMoims(title: String): Flow<ApiResult<List<MoimItem>>> {
        return flow {
            emit(remoteDataSource.getMoims(title).convert { it.map { dto -> dto.toMoimItem() } })
        }.catch {
            emit(ApiResult.Error(it))
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getMoimMembers(joinMember: String): Flow<ApiResult<List<Profile>>> {
        return flow {
            emit(remoteDataSource.getMoimMember(joinMember).convert { it.map { dto -> dto.toProfile() } })
        }.catch {
            emit(ApiResult.Error(it))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun joinMoim(id: String, title: String, date: String): Flow<ApiResult<MoimItem>> {
        return flow {
            emit(remoteDataSource.joinMoim(id, title, date).convert { it.toMoimItem() })
        }.catch {
            emit(ApiResult.Error(it))
        }.flowOn(Dispatchers.IO)
    }
}
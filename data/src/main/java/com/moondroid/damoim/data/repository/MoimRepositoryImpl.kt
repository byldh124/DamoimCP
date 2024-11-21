package com.moondroid.damoim.data.repository

import com.google.gson.Gson
import com.moondroid.damoim.data.datasource.local.LocalDataSource
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toMoimItem
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.model.request.CreateMoimRequest
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.model.status.doInFlow
import com.moondroid.damoim.domain.repository.MoimRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoimRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : MoimRepository {

    override suspend fun createMoim(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String,
    ): Flow<ApiResult<Unit>> = doInFlow {
        val request =
            CreateMoimRequest(title, address, date, time, pay, lat, lng, Gson().toJson(listOf(joinMember)))
        emit(remoteDataSource.createMoim(request))
    }

    override suspend fun getMoims(title: String): Flow<ApiResult<List<MoimItem>>> = doInFlow {
        emit(remoteDataSource.getMoims(title).convert { it.map { dto -> dto.toMoimItem() } })
    }


    override suspend fun getMoimMembers(joinMember: String): Flow<ApiResult<List<Profile>>> = doInFlow {
        emit(remoteDataSource.getMoimMember(joinMember).convert { it.map { dto -> dto.toProfile() } })
    }

    override suspend fun joinMoim(title: String, date: String): Flow<ApiResult<MoimItem>> = doInFlow {
        val id = localDataSource.getId()
        emit(remoteDataSource.joinMoim(id, title, date).convert { it.toMoimItem() })
    }
}
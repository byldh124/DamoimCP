package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.constant.NoResult
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MoimRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : MoimRepository {

    override fun createMoim(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String,
    ): Flow<ApiResult<NoResult>> = doInFlow {
        val request =
            CreateMoimRequest(title, address, date, time, pay, lat, lng, Json.encodeToString(listOf(joinMember)))
        emit(remoteDataSource.createMoim(request))
    }

    override fun getMoims(title: String): Flow<ApiResult<List<MoimItem>>> = doInFlow {
        emit(remoteDataSource.getMoims(title).convert { it.map { dto -> dto.toMoimItem() } })
    }


    override fun getMoimMembers(joinMember: String): Flow<ApiResult<List<Profile>>> = doInFlow {
        emit(remoteDataSource.getMoimMember(joinMember).convert { it.map { dto -> dto.toProfile() } })
    }

    override fun joinMoim(title: String, date: String): Flow<ApiResult<MoimItem>> = doInFlow {
        val id = localDataSource.getId()
        emit(remoteDataSource.joinMoim(id, title, date).convert { it.toMoimItem() })
    }
}
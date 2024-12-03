package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface MoimRepository {
    suspend fun createMoim(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String
    ): Flow<ApiResult<NoResult>>

    suspend fun getMoims(title: String): Flow<ApiResult<List<MoimItem>>>

    suspend fun getMoimMembers(joinMember: String) : Flow<ApiResult<List<Profile>>>

    suspend fun joinMoim(title: String, date: String) : Flow<ApiResult<MoimItem>>
}
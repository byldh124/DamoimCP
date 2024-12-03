package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {

    suspend fun getProfile(): Flow<ApiResult<Profile>>

    suspend fun updateToken(token: String): Flow<ApiResult<NoResult>>

    suspend fun updateInterest(interest: String): Flow<ApiResult<NoResult>>

    suspend fun deleteProfile(): Flow<ApiResult<NoResult>>

    suspend fun updateProfile(
        name: String,
        birth: String,
        gender: String,
        location: String,
        message: String,
        thumb: File?,
    ): Flow<ApiResult<Profile>>
}
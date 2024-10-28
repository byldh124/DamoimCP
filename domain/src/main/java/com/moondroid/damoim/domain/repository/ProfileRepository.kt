package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {

    suspend fun getProfile(): Flow<ApiResult<Profile>>

    suspend fun updateToken(id: String, token: String): Flow<ApiResult<Unit>>

    suspend fun updateInterest(interest: String): Flow<ApiResult<Unit>>

    suspend fun deleteProfile(): Flow<ApiResult<Boolean>>

    suspend fun updateProfile(
        id: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        message: String,
        thumb: File?,
    ): Flow<ApiResult<Profile>>
}
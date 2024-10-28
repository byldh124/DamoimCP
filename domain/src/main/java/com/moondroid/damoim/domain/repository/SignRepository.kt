package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface SignRepository {
    suspend fun signUp(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String
    ): Flow<ApiResult<Profile>>

    suspend fun signIn(id: String, hashPw: String): Flow<ApiResult<Profile>>
    suspend fun getSalt(id: String): Flow<ApiResult<String>>
    suspend fun socialSign(id: String): Flow<ApiResult<Profile>>
    suspend fun resign(id: String): Flow<ApiResult<Unit>>
}
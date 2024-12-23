package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface SignRepository {
    fun signUp(
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

    fun signIn(id: String, hashPw: String): Flow<ApiResult<Profile>>
    fun getSalt(id: String): Flow<ApiResult<String>>
    fun socialSign(id: String): Flow<ApiResult<Profile>>
    fun resign(id: String): Flow<ApiResult<NoResult>>
}
package com.moondroid.damoim.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val id: String,
    val hashPw: String
)
package com.moondroid.damoim.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTokenRequest(
    val id: String,
    val token: String
)

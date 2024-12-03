package com.moondroid.damoim.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateMoimRequest(
    val title: String,
    val address: String,
    val date: String,
    val time: String,
    val pay: String,
    val lat: Double,
    val lng: Double,
    val joinMember: String
)
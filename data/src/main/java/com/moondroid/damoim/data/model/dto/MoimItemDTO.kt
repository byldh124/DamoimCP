package com.moondroid.damoim.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class MoimItemDTO(
    val title: String,
    val address: String,
    val date: String,
    val time: String,
    val pay: String,
    val lat: Double,
    val lng: Double,
    val joinMember: String
)

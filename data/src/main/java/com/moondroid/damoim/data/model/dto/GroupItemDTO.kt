package com.moondroid.damoim.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class GroupItemDTO(
    val no: Int,
    val title: String,
    val location: String,
    val purpose: String,
    val interest: String,
    val thumb: String,
    val image: String,
    val information: String,
    val masterId: String,
)
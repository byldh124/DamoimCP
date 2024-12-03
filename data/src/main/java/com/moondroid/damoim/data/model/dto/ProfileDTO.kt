package com.moondroid.damoim.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
    var id: String,
    var name: String,
    var birth: String,
    var gender: String,
    var location: String,
    var interest: String,
    var thumb: String,
    var message: String,
)
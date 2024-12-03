package com.moondroid.damoim.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val id : String,
    val hashPw: String,
    val salt : String,
    val name: String,
    val birth: String,
    val gender: String,
    val location: String,
    val interest: String,
    val thumb: String
)
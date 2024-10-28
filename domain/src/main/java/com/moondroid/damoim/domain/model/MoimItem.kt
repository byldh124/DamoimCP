package com.moondroid.damoim.domain.model

data class MoimItem(
    val title: String,
    val address: String,
    val date: String,
    val time: String,
    val pay: String,
    val lat: Double,
    val lng: Double,
    val joinMember: String
)

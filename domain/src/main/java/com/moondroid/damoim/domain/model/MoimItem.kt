package com.moondroid.damoim.domain.model

data class MoimItem(
    val title: String = "",
    val address: String = "",
    val date: String = "",
    val time: String = "",
    val pay: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val joinMember: String = "",
)

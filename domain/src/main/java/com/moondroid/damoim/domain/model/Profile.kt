package com.moondroid.damoim.domain.model

data class Profile(
    var id: String,
    var name: String,
    var birth: String,
    var gender: String,
    var location: String,
    var interest: String,
    var thumb: String,
    var message: String,
)

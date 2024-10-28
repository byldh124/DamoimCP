package com.moondroid.damoim.data.model.response

import com.google.gson.annotations.SerializedName
import com.moondroid.damoim.common.RequestParam

data class SimpleResponse(
    @SerializedName(RequestParam.CODE)
    val code: Int,
    @SerializedName(RequestParam.MESSAGE)
    val message: String? = null,
    @SerializedName(RequestParam.RESULT)
    val result: String
)
package com.moondroid.damoim.data.model.response

import com.moondroid.damoim.common.constant.ResponseCode


data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val result: T,
) {
    fun isSuccess() = code == ResponseCode.SUCCESS
}
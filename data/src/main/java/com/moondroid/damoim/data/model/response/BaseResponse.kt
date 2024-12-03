package com.moondroid.damoim.data.model.response

import com.moondroid.damoim.common.constant.ResponseCode
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val result: T,
)

inline fun <reified T> BaseResponse<T>.toApiResult(): ApiResult<T> {
    return try {
        if (code == ResponseCode.SUCCESS) {
            ApiResult.Success(result)
        } else {
            ApiResult.Fail(code)
        }
    } catch (e: Exception) {
        ApiResult.Error(e)
    }
}
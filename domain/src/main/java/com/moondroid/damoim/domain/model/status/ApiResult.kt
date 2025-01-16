package com.moondroid.damoim.domain.model.status

import com.moondroid.damoim.common.constant.NoResult
import com.moondroid.damoim.common.constant.ResponseCode
import com.moondroid.damoim.common.exception.DMException
import com.moondroid.damoim.common.util.debug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

data class FailResponse(val code: Int, val message: String) {}


sealed class ApiResult<out T> {
    class SuccessWithoutResult<out T> : ApiResult<T>()

    //ResponseCode = 1000(o)
    data class Success<out T>(val response: T) : ApiResult<T>()

    //ResponseCode = 1000(x)
    data class Fail<T>(val fail: FailResponse) : ApiResult<T>()

    //통신 에러
    data class Error<T>(val throwable: Throwable) : ApiResult<T>()

    inline fun <D> convert(mapper: (T) -> D): ApiResult<D> {
        return when (this) {
            is Error -> Error(throwable)
            is Fail -> Fail(fail)
            is SuccessWithoutResult -> SuccessWithoutResult()
            is Success -> Success(mapper(response))
        }
    }
}

inline fun <T> ApiResult<T>.onSuccessWithoutResult(action: () -> Unit): ApiResult<T> {
    if (this is ApiResult.SuccessWithoutResult) action()
    return this
}

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(response)
    return this
}

inline fun <T> ApiResult<T>.onFail(action: (FailResponse) -> Unit): ApiResult<T> {
    if (this is ApiResult.Fail) action(fail)
    return this
}

inline fun <T> ApiResult<T>.onError(action: (Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) action(throwable)
    return this
}

fun <T> doInFlow(scope: suspend FlowCollector<ApiResult<T>>.() -> Unit): Flow<ApiResult<T>> {
    return flow {
        scope(this)
    }.catch {
        debug("logException: ${it.stackTraceToString()}")
        when (it) {
            is DMException.ProfileException -> {
                emit(ApiResult.Fail(FailResponse(ResponseCode.PROFILE_ERROR, "액세스 토큰 만료")))
            }
            else -> emit(ApiResult.Error(it))
        }
    }.flowOn(Dispatchers.IO)
}
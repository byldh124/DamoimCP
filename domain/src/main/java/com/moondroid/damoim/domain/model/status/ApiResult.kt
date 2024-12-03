package com.moondroid.damoim.domain.model.status

import com.moondroid.damoim.common.constant.ResponseCode
import com.moondroid.damoim.common.exception.DMException
import com.moondroid.damoim.common.util.debug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

sealed class ApiResult<out T> {
    //ResponseCode = 1000(o)
    data class Success<out T>(val response: T) : ApiResult<T>()

    //ResponseCode = 1000(x)
    data class Fail<T>(val code: Int) : ApiResult<T>()

    //통신 에러
    data class Error<T>(val throwable: Throwable) : ApiResult<T>()

    inline fun <D> convert(mapper: (T) -> D): ApiResult<D> {
        return when (this) {
            is Error -> Error(throwable)
            is Fail -> Fail(code)
            is Success -> Success(mapper(response))
        }
    }
}

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(response)
    return this
}

inline fun <T> ApiResult<T>.onFail(action: (Int) -> Unit): ApiResult<T> {
    if (this is ApiResult.Fail) action(code)
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
            is DMException.ProfileException -> emit(ApiResult.Fail(ResponseCode.PROFILE_ERROR))
            else -> emit(ApiResult.Error(it))
        }
    }.flowOn(Dispatchers.IO)
}
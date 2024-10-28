package com.moondroid.damoim.data.api.response

sealed class ApiStatus<out T> {
    data class Success<out T>(val response: T) : ApiStatus<T>()
    data class Error<T>(val throwable: Throwable) : ApiStatus<T>()
}

class ApiException(val code: Int) : RuntimeException()
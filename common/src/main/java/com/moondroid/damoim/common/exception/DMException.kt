package com.moondroid.damoim.common.exception

sealed class DMException : Exception() {
    class ProfileException : DMException()
    class HttpStatusException(val code: Int): DMException()
    class NoResultException: DMException()
}
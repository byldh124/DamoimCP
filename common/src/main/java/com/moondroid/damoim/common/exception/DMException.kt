package com.moondroid.damoim.common.exception

sealed class DMException : Exception() {
    class ProfileException : DMException()
}
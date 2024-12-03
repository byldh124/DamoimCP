package com.moondroid.damoim.data.api

import com.moondroid.damoim.common.exception.DMException
import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.jvm.Throws

@Throws(
    DMException::class,
    SerializationException::class,
    IllegalArgumentException::class,
    NoTransformationFoundException::class,
    DoubleReceiveException::class
)
suspend inline fun <reified T> HttpResponse.parseBody(): T {
    if (status.value in 200..208) {
        return if (headers["Content-Type"]?.contains("application/json") == false) {
            val stringResponse = body<String>()
            val jsonBuilder = Json {
                ignoreUnknownKeys = true
            }
            jsonBuilder.decodeFromString<T>(stringResponse)
        } else {
            body<T>()
        }
    } else {
        throw DMException.HttpStatusException(status.value)
    }
}
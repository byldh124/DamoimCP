package com.moondroid.damoim.data.api.ktor

import com.moondroid.damoim.common.exception.DMException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json

suspend inline fun <reified T> HttpClient.work(
    url: String = "",
    httpMethod: HttpMethod = HttpMethod.Get,
    builder: HttpRequestBuilder.() -> Unit = {}
): T {
    val httpResponse: HttpResponse = request(url) {
        method = httpMethod
        builder()
    }

    if (httpResponse.status.value in 200..208) {
        return if (httpResponse.headers["Content-Type"]?.contains("application/json") == false) {
            val stringResponse = httpResponse.body<String>()
            val jsonBuilder = Json {
                ignoreUnknownKeys = true
            }
            jsonBuilder.decodeFromString<T>(stringResponse)
        } else {
            httpResponse.body<T>()
        }
    } else {
        throw DMException.HttpStatusException(httpResponse.status.value)
    }
}
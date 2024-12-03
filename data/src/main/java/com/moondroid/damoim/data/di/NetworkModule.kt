package com.moondroid.damoim.data.di

import com.moondroid.damoim.data.api.URLManager.BASE_URL
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.datasource.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideKtorHttpClient(): HttpClient {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                        encodeDefaults = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
            defaultRequest {
                url(BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
        return client
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}
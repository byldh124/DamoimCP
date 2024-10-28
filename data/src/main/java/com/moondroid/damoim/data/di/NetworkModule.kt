package com.moondroid.damoim.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.Strictness
import com.moondroid.damoim.data.BuildConfig
import com.moondroid.damoim.data.api.ApiInterface
import com.moondroid.damoim.data.api.URLManager.BASE_URL
import com.moondroid.damoim.data.api.response.ResponseAdapterFactory
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.datasource.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.EMPTY_RESPONSE
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.lang.reflect.Type
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    class ResponseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            val modified = response.newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .build()
            return modified
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor {
            val log = try {
                JSONObject(it).toString()
            } catch (e: JSONException) {
                //URLDecoder.decode(it, "UTF-8")
                it
            }
            Log.d("HttpClient", log)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(ResponseInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ResponseAdapterFactory())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setStrictness(Strictness.LENIENT).create())


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    /**
     * 비어있는(length=0)인 Response를 받았을 경우 처리
     */
    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit,
        ): Converter<ResponseBody, *> {
            val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter EMPTY_RESPONSE
                delegate.convert(it)
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}
package com.moondroid.damoim.data.api.response

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ResponseCall<T> constructor(
    private val callDelegate: Call<T>
) : Call<ApiStatus<T>> {

    override fun enqueue(callback: Callback<ApiStatus<T>>) = callDelegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let {
                when (response.code()) {
                    in 200..208 -> {
                        callback.onResponse(this@ResponseCall, Response.success(ApiStatus.Success(it)))
                    }

                    in 400..409 -> {
                        callback.onResponse(
                            this@ResponseCall,
                            Response.success(ApiStatus.Error(ApiException(response.code())))
                        )
                    }
                }
            } ?: callback.onResponse(
                this@ResponseCall, Response.success(
                    ApiStatus.Error(
                        Throwable("Data not found")
                    )
                )
            )
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onResponse(this@ResponseCall, Response.success(ApiStatus.Error(t)))
            call.cancel()
        }
    })

    override fun clone(): Call<ApiStatus<T>> = ResponseCall(callDelegate.clone())

    override fun execute(): Response<ApiStatus<T>> =
        throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()
}
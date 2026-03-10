package com.example.demo.core.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
    private val tokenProvider: () -> String?,
    private val deviceIdProvider: () -> String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        // Token add
        tokenProvider()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        requestBuilder.addHeader("deviceid", deviceIdProvider())

        val newRequest = requestBuilder.build()

        return chain.proceed(newRequest)
    }
}
package com.example.demo.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(

    private val requestInterceptor: RequestInterceptor,
    private val responseInterceptor: ResponseInterceptor

) {

    companion object {
        private const val BASE_URL = "https://bc.finrichtechnology.com/"
    }

    private val okHttpClient: OkHttpClient by lazy {

        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(responseInterceptor)
            .build()

    }

    private val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

}
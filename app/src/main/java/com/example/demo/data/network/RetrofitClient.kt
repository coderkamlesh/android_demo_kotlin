package com.example.demo.data.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://your-api.com/"

    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                // 1. Request intercept karo aur Token add karo
                val token = "your_saved_token" // Prefs se uthao
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                
                val response = chain.proceed(newRequest)

                // 2. Response check karo (401 logic)
                if (response.code == 401) {
                    // Session clear karke login pe bhejo
                    // context.startActivity(LoginIntent)
                }
                response
            }
            .build()
    }

    fun getInstance(context: Context): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
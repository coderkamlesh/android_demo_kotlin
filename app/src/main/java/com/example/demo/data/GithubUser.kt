package com.example.demo.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class GithubUser(
    val id: Int,
    val login: String,
    val avatar_url: String // API ke key name se match hona chahiye
)

// API Interface
interface GithubApi {
    @GET("users")
    suspend fun getUsers(): List<GithubUser>
}

// Retrofit Object (Singleton)
object RetrofitInstance {
    val api: GithubApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}
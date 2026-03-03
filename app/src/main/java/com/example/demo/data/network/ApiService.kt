package com.example.demo.data.network

interface ApiService {
    @GET("users/profile")
    suspend fun getUserProfile(): Response<UserResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<UserResponse>
}
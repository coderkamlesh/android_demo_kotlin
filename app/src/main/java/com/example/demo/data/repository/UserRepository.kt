package com.example.demo.data.repository

import com.example.demo.data.network.ApiService

class UserRepository(private val apiService: ApiService) {
    suspend fun fetchProfile() = apiService.getUserProfile()
}
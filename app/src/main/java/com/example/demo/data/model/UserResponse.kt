package com.example.demo.data.model

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val token: String? = null
)
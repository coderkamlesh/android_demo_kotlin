package com.example.demo.features.auth.data.model

data class LoginResponse(
    val token: String,
    val userData: UserData
)

data class UserData(
    val userId: String,
    val name: String,
    val roleId: Int,
    val mobile: String,
    val address: String
)
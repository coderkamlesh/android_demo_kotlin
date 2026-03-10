package com.example.demo.features.auth.data.model

data class ValidateOtpRequest(
    val username: String,
    val otp: String,
    val password: String,
    val source: String = "WEB"
)
package com.example.demo.features.auth.data.model

data class LoginRequest(
    val username: String,
    val password: String,
    val otpMode: String = "VIA_WHATSAPP",
    val source: String = "WEB"
)
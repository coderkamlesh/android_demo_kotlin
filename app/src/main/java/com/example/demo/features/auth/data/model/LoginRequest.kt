package com.example.demo.features.auth.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("otpMode") val otpMode: String = "VIA_WHATSAPP",
    @SerializedName("source") val source: String = "APP"
)

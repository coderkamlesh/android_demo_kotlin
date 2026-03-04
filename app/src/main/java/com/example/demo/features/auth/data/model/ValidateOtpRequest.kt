package com.example.demo.features.auth.data.model

import com.google.gson.annotations.SerializedName

data class ValidateOtpRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("otp") val otp: String,
    @SerializedName("source") val source: String = "APP"
)

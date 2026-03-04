package com.example.demo.core.network

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("data")
    val data: T?
)

package com.example.demo.core.network

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val errorMessage: String?,
    val data: T?
)

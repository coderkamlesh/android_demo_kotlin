package com.example.demo.features.auth.domain.repository

import com.example.demo.core.network.BaseResponse
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import com.example.demo.features.auth.data.model.LoginResponse

interface AuthRepository {

    suspend fun login(
        request: LoginRequest
    ): BaseResponse<Any?>

    suspend fun verifyOtp(
        request: ValidateOtpRequest
    ): BaseResponse<LoginResponse>

}
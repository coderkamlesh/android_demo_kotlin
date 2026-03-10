package com.example.demo.features.auth.data.repository

import com.example.demo.features.auth.data.remote.AuthApi
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import com.example.demo.features.auth.domain.repository.AuthRepository
import com.example.demo.core.network.BaseResponse
import com.example.demo.features.auth.data.model.LoginResponse

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(
        request: LoginRequest
    ): BaseResponse<Any?> {
        return api.login(request)
    }

    override suspend fun verifyOtp(
        request: ValidateOtpRequest
    ): BaseResponse<LoginResponse> {
        return api.validateOtp(request)
    }

}
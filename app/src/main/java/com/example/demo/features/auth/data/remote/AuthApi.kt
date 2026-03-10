package com.example.demo.features.auth.data.remote

import com.example.demo.core.network.BaseResponse
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import com.example.demo.features.auth.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login/userLogin")
    suspend fun login(
        @Body request: LoginRequest
    ): BaseResponse<Any?>

    @POST("login/validateOtp")
    suspend fun validateOtp(
        @Body request: ValidateOtpRequest
    ): BaseResponse<LoginResponse>

}
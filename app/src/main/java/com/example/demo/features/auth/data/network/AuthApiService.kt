package com.example.demo.features.auth.data.network

import com.example.demo.core.network.BaseResponse
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.data.model.LoginResponseData
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login/userLogin")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<BaseResponse<Nothing?>>

    @POST("login/validateOtp")
    suspend fun validateOtp(
        @Body request: ValidateOtpRequest
    ): Response<BaseResponse<LoginResponseData>>
}

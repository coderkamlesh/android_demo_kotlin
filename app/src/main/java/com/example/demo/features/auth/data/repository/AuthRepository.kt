package com.example.demo.features.auth.data.repository

import com.example.demo.core.network.BaseResponse
import com.example.demo.core.network.NetworkResult
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.data.model.LoginResponseData
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import com.example.demo.features.auth.data.network.AuthApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(private val apiService: AuthApiService) {

    suspend fun login(request: LoginRequest): Flow<NetworkResult<BaseResponse<Nothing?>>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.loginUser(request)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                emit(NetworkResult.Error("Login Failed"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "Network Error"))
        }
    }

    suspend fun validateOtp(request: ValidateOtpRequest): Flow<NetworkResult<BaseResponse<LoginResponseData>>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.validateOtp(request)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                emit(NetworkResult.Error("OTP Failed"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.localizedMessage ?: "Network Error"))
        }
    }
}

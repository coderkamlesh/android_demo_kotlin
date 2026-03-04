package com.example.demo.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.core.network.BaseResponse
import com.example.demo.core.network.NetworkResult
import com.example.demo.core.utils.SessionManager
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.data.model.LoginResponseData
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import com.example.demo.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<NetworkResult<BaseResponse<Nothing?>>?>(null)
    val loginState: StateFlow<NetworkResult<BaseResponse<Nothing?>>?> = _loginState

    private val _otpState = MutableStateFlow<NetworkResult<BaseResponse<LoginResponseData>>?>(null)
    val otpState: StateFlow<NetworkResult<BaseResponse<LoginResponseData>>?> = _otpState
    
    // New StateFlow for token expiry event triggered by Interceptor
    private val _tokenExpiredEvent = MutableStateFlow(false)
    val tokenExpiredEvent: StateFlow<Boolean> = _tokenExpiredEvent

    fun isLoggedIn(): Boolean = sessionManager.getToken() != null

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            repository.login(request).collect { _loginState.value = it }
        }
    }

    fun validateOtp(request: ValidateOtpRequest) {
        viewModelScope.launch {
            repository.validateOtp(request).collect { result ->
                if (result is NetworkResult.Success) {
                    result.data.data?.token?.let { sessionManager.saveToken(it) }
                }
                _otpState.value = result
            }
        }
    }

    fun resetStates() {
        _loginState.value = null
        _otpState.value = null
    }
    
    fun onTokenExpiredConsumed() {
        _tokenExpiredEvent.value = false
    }
    
    fun triggerTokenExpiry() {
        sessionManager.clearSession()
        _tokenExpiredEvent.value = true
    }
}

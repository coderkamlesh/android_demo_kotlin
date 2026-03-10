package com.example.demo.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.core.datastore.SessionManager
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import com.example.demo.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun login(
        mobile: String,
        password: String,
        onOtpRequired: () -> Unit,
        onLoginSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            val response = repository.login(
                LoginRequest(
                    username = mobile,
                    password = password
                )
            )

            when (response.status) {

                12 -> {
                    onOtpRequired()
                }

                1 -> {
                    onLoginSuccess()
                }

            }

        }
    }

    fun verifyOtp(
        mobile: String,
        password: String,
        otp: String,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            val response = repository.verifyOtp(
                ValidateOtpRequest(
                    username = mobile,
                    password = password,
                    otp = otp
                )
            )

            if (response.status == 1 && response.data != null) {

                val data = response.data

                sessionManager.saveSession(
                    token = data.token,
                    userId = data.userData.userId,
                    name = data.userData.name,
                    mobile = data.userData.mobile,
                    roleId = data.userData.roleId
                )

                onSuccess()
            }

        }

    }

}
package com.example.demo.core.utils

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(getToken() != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
        _isLoggedIn.value = true
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
        _isLoggedIn.value = false
    }
}

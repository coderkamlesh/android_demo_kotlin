package com.example.demo.core.utils

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(getToken() != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun saveToken(token: String) {
        prefs.edit { putString("auth_token", token) }
        _isLoggedIn.value = true
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearSession() {
        prefs.edit { clear() }
        _isLoggedIn.value = false
    }
}

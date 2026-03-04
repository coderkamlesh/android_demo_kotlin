package com.example.demo.core.network

import android.content.Context
import com.example.demo.core.utils.DeviceUtils
import com.example.demo.core.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context,
    private val sessionManager: SessionManager
) : Interceptor {
    private val deviceId = DeviceUtils.getDeviceId(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader("deviceid", deviceId)

        // Har request mein token add karna agar available ho
        sessionManager.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        val response = chain.proceed(requestBuilder.build())

        // 401 (Unauthorized) ya 403 (Forbidden) par logout logic
        if (response.code == 401 || response.code == 403) {
            sessionManager.clearSession()
        }

        return response
    }
}

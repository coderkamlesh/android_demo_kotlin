package com.example.demo.core.network

import com.example.demo.core.datastore.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor(
    private val sessionManager: SessionManager,
    private val logoutHandler: () -> Unit
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401 || response.code == 403) {

            // Session clear
            runBlocking {
                sessionManager.clearSession()
            }

            // UI ko notify karo
            logoutHandler()

        }

        return response
    }
}
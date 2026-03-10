package com.example.demo.features.auth.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.demo.core.datastore.SessionManager
import com.example.demo.core.navigation.Routes
import com.example.demo.core.network.RetrofitClient
import com.example.demo.core.network.RequestInterceptor
import com.example.demo.core.network.ResponseInterceptor
import com.example.demo.core.utils.DeviceUtils
import com.example.demo.features.auth.data.remote.AuthApi
import com.example.demo.features.auth.data.repository.AuthRepositoryImpl
import com.example.demo.features.auth.presentation.viewmodel.AuthViewModel
import com.example.demo.features.auth.presentation.viewmodel.AuthViewModelFactory

@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current

    val sessionManager = remember {
        SessionManager(context.applicationContext)
    }

    val requestInterceptor = remember {
        RequestInterceptor(
            tokenProvider = { null },
            deviceIdProvider = { DeviceUtils.getDeviceId(context) }
        )
    }

    val responseInterceptor = remember {
        ResponseInterceptor(
            sessionManager = sessionManager,
            logoutHandler = {}
        )
    }

    val retrofitClient = remember {
        RetrofitClient(requestInterceptor, responseInterceptor)
    }

    val api = remember {
        retrofitClient.createService(AuthApi::class.java)
    }

    val repository = remember {
        AuthRepositoryImpl(api)
    }

    val factory = remember {
        AuthViewModelFactory(repository, sessionManager)
    }

    val viewModel: AuthViewModel = viewModel(factory = factory)

    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            label = { Text("Mobile") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                viewModel.login(
                    mobile = mobile,
                    password = password,

                    onOtpRequired = {
                        navController.navigate(Routes.Otp.route)
                    },

                    onLoginSuccess = {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(Routes.Login.route) { inclusive = true }
                        }
                    }
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

    }
}
package com.example.demo.features.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demo.core.network.NetworkResult
import com.example.demo.features.auth.data.model.LoginRequest
import com.example.demo.features.auth.viewmodel.AuthViewModel
import com.example.demo.core.navigation.Screen

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is NetworkResult.Success) {
            val res = (loginState as NetworkResult.Success).data
            if (res.status == 12) {
                // OTP sent successfully, navigate to OTP screen with params
                navController.navigate(Screen.Otp.createRoute(mobile, password))
            } else if (res.status == 1) {
                // Direct login successful
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (mobile.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.loginUser(LoginRequest(username = mobile, password = password))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = loginState !is NetworkResult.Loading
        ) {
            Text("Login / Get OTP")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (loginState) {
            is NetworkResult.Loading -> CircularProgressIndicator()
            is NetworkResult.Error -> {
                val errorMsg = (loginState as NetworkResult.Error).message
                Text(text = errorMsg, color = Color.Red)
            }
            else -> {}
        }
    }
}

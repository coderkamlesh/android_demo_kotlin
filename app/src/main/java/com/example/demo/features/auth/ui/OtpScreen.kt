package com.example.demo.features.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demo.core.network.NetworkResult
import com.example.demo.features.auth.data.model.ValidateOtpRequest
import com.example.demo.features.auth.viewmodel.AuthViewModel
import com.example.demo.core.navigation.Screen

@Composable
fun OtpScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    username: String,
    password: String
) {
    var otp by remember { mutableStateOf("") }
    val otpState by viewModel.otpState.collectAsState()

    LaunchedEffect(otpState) {
        if (otpState is NetworkResult.Success) {
            val res = (otpState as NetworkResult.Success).data
            if (res.status == 1) {
                viewModel.resetStates()
                // Home pe bhej do aur Auth stack clear kar do
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Verify OTP", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "OTP sent to your WhatsApp", style = MaterialTheme.typography.bodyMedium)
        
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("Enter 6-digit OTP") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { 
                if (otp.length >= 4) {
                    viewModel.validateOtp(
                        ValidateOtpRequest(
                            username = username,
                            password = password,
                            otp = otp,
                            source = "APP"
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = otpState !is NetworkResult.Loading
        ) {
            Text("Verify & Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (otpState is NetworkResult.Loading) CircularProgressIndicator()
        if (otpState is NetworkResult.Error) {
            Text(text = (otpState as NetworkResult.Error).message, color = Color.Red)
        }
    }
}

package com.example.demo.core.navigation

sealed class Routes(val route: String) {

    // Auth flow
    data object Login : Routes("login")
    data object Otp : Routes("otp")

    // Bottom tabs
    data object Home : Routes("home")
    data object Reports : Routes("reports")
    data object Profile : Routes("profile")

}
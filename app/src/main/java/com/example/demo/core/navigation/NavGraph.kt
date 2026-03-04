package com.example.demo.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.demo.features.home.ui.HomeScreen
import com.example.demo.features.reports.ui.ReportsScreen
import com.example.demo.features.profile.ui.ProfileScreen
import com.example.demo.features.auth.ui.LoginScreen
import com.example.demo.features.auth.ui.OtpScreen
import com.example.demo.features.auth.viewmodel.AuthViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Reports : Screen("reports")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Otp : Screen("otp/{username}/{password}") {
        fun createRoute(username: String, password: String) = "otp/$username/$password"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(
            route = Screen.Otp.route,
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            OtpScreen(navController, authViewModel, username, password)
        }

        composable(Screen.Home.route) {
            HomeScreen()
        }

        composable(Screen.Reports.route) {
            ReportsScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController, authViewModel)
        }
    }
}

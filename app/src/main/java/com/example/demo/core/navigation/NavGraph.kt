package com.example.demo.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.demo.core.datastore.SessionManager
import com.example.demo.features.auth.presentation.ui.LoginScreen
import com.example.demo.features.auth.presentation.ui.OtpScreen
import com.example.demo.features.home.presentation.ui.HomeScreen
import com.example.demo.features.profile.presentation.ui.ProfileScreen
import com.example.demo.features.reports.presentation.ui.ReportsScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context.applicationContext) }

    val token by sessionManager.token.collectAsState(initial = null)

    val startDestination = if (token.isNullOrEmpty()) {
        Routes.Login.route
    } else {
        Routes.Home.route
    }

    Scaffold(
        bottomBar = {
            val route = navController.currentBackStackEntry?.destination?.route
            if (route == Routes.Home.route ||
                route == Routes.Reports.route ||
                route == Routes.Profile.route
            ) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Routes.Login.route) {
                LoginScreen(navController)
            }

            composable(Routes.Otp.route) {
                OtpScreen(navController)
            }

            composable(Routes.Home.route) {
                HomeScreen(navController)
            }

            composable(Routes.Reports.route) {
                ReportsScreen(navController)
            }

            composable(Routes.Profile.route) {
                ProfileScreen(navController)
            }

        }
    }
}
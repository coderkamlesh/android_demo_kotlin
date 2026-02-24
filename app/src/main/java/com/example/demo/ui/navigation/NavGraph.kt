package com.example.demo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demo.ui.screens.HomeScreen
import com.example.demo.ui.screens.ReportsScreen
import com.example.demo.ui.screens.ProfileScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Reports : Screen("reports")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Reports.route) { ReportsScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
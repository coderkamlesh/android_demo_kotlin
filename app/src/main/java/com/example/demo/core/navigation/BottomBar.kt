package com.example.demo.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == Routes.Home.route,
            onClick = { navController.navigate(Routes.Home.route) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Reports.route,
            onClick = { navController.navigate(Routes.Reports.route) },
            icon = { Icon(Icons.Filled.List, contentDescription = "Reports") },
            label = { Text("Reports") }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Profile.route,
            onClick = { navController.navigate(Routes.Profile.route) },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )

    }
}
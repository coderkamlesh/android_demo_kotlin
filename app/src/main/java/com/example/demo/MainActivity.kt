package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.demo.core.navigation.NavGraph
import com.example.demo.core.navigation.Screen
import com.example.demo.core.network.AuthInterceptor
import com.example.demo.core.network.RetrofitClient
import com.example.demo.core.ui.theme.DemoTheme
import com.example.demo.core.utils.SessionManager
import com.example.demo.features.auth.data.network.AuthApiService
import com.example.demo.features.auth.data.repository.AuthRepository
import com.example.demo.features.auth.viewmodel.AuthViewModel
import com.example.demo.features.auth.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        val authInterceptor = AuthInterceptor(this, sessionManager)
        val retrofit = RetrofitClient.getRetrofit(this, authInterceptor)
        val authApiService = retrofit.create(AuthApiService::class.java)
        val repository = AuthRepository(authApiService)
        
        val authViewModel: AuthViewModel = ViewModelProvider(
            this, AuthViewModelFactory(repository, sessionManager)
        )[AuthViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            DemoTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val isTokenExpired by authViewModel.tokenExpiredEvent.collectAsState()
                
                LaunchedEffect(isTokenExpired) {
                    if (isTokenExpired) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                        authViewModel.onTokenExpiredConsumed()
                    }
                }

                val authRoutes = listOf(Screen.Login.route, Screen.Otp.route)
                val showBottomBar = currentRoute !in authRoutes && currentRoute != null

                val startDest = if (authViewModel.isLoggedIn()) Screen.Home.route else Screen.Login.route

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                val items = listOf(Screen.Home, Screen.Reports, Screen.Profile)
                                items.forEach { screen ->
                                    val icon = when (screen) {
                                        Screen.Home -> Icons.Default.Home
                                        Screen.Reports -> Icons.AutoMirrored.Filled.List
                                        Screen.Profile -> Icons.Default.Person
                                        else -> Icons.Default.Home
                                    }
                                    NavigationBarItem(
                                        icon = { Icon(icon, contentDescription = null) },
                                        label = { Text(screen.route.replaceFirstChar { it.uppercase() }) },
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(
                            navController = navController,
                            startDestination = startDest,
                            authViewModel = authViewModel
                        )
                    }
                }
            }
        }
    }
}

package com.example.demo.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.data.GithubUser
import com.example.demo.data.RetrofitInstance
import kotlinx.coroutines.launch

class GithubViewModel : ViewModel() {
    var users by mutableStateOf<List<GithubUser>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    init { fetchUsers() }

    fun fetchUsers() {
        viewModelScope.launch {
            isLoading = true
            try {
                users = RetrofitInstance.api.getUsers()
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Network Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
package com.meproject.newsstream.presentation.ui.auth

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
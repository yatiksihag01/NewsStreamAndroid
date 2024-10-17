package com.meproject.newsstream.presentation.ui.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToHome: Boolean = false
)
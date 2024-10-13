package com.meproject.newsstream.presentation.ui.auth

data class SignupUiState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isSignUpEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val confirmPasswordError: String? = null,
    val errorMessage: String? = null
)


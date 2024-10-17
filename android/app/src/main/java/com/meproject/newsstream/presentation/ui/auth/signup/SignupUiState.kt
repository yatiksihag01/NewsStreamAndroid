package com.meproject.newsstream.presentation.ui.auth.signup

data class SignupUiState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val securityQuestion: String = "",
    val answer: String = "",
    val isSignUpEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val confirmPasswordError: String? = null,
    val errorMessage: String? = null,
    val navigateToPreference: Boolean = false
)


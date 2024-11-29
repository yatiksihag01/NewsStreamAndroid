package com.meproject.newsstream.presentation.ui.auth.login

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    data object LoginClick : LoginUiEvent()
    data object LoggedIn: LoginUiEvent()
}
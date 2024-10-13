package com.meproject.newsstream.presentation.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            isLoginEnabled = isLoginEnabled()
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            isLoginEnabled = isLoginEnabled()
        )
    }

    //TODO: Call login use case
    fun onLoginClick() {
        _uiState.value = _uiState.value.copy(isLoading = true)
    }

    fun onSignUpClick() {
        TODO()
    }

    fun onForgotPasswordClick() {
        TODO()
    }

    private fun isLoginEnabled(): Boolean {
        return _uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank()
    }
}
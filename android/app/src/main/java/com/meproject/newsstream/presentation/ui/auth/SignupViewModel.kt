package com.meproject.newsstream.presentation.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(SignupUiState())
    val uiState: State<SignupUiState> = _uiState

    fun onNameChange(newUserName: String) {
        _uiState.value = _uiState.value.copy(
            userName = newUserName,
            isSignUpEnabled = validateForm()
        )
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(
            email = newEmail,
            isSignUpEnabled = validateForm()
        )
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(
            password = newPassword,
            isSignUpEnabled = validateForm()
        )
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = newConfirmPassword,
            isSignUpEnabled = validateForm()
        )
    }

    private fun validateForm(): Boolean {
        return _uiState.value.userName.isNotBlank() &&
                _uiState.value.email.isNotBlank() &&
                _uiState.value.password.isNotBlank() &&
                _uiState.value.confirmPassword == _uiState.value.password
    }

    // TODO: Call signup use case
    fun onSignUpClick() {
        if (validateForm()) {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Call signup use case (e.g., async process)
        }
    }
}
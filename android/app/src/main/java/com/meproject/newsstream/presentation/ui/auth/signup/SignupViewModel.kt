package com.meproject.newsstream.presentation.ui.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(SignupUiState())
    val uiState: State<SignupUiState> = _uiState

    fun handleEvent(event: SignupUiEvent) {
        when (event) {
            is SignupUiEvent.NameChanged -> onNameChange(event.name)
            is SignupUiEvent.EmailChanged -> onEmailChange(event.email)
            is SignupUiEvent.PasswordChanged -> onPasswordChange(event.password)
            is SignupUiEvent.ConfirmPasswordChanged -> onConfirmPasswordChange(event.confirmPassword)
            is SignupUiEvent.SecurityQuestionChanged -> onSecurityQuestionChange(event.question)
            is SignupUiEvent.SecurityAnswerChanged -> onSecurityAnswerChange(event.answer)
            SignupUiEvent.SignUpClick -> onSignUpClick()
            SignupUiEvent.SignedUp -> {
                _uiState.value = _uiState.value.copy(
                    navigateToPreference = false
                )
            }
        }
    }

    private fun onSecurityQuestionChange(securityQuestion: String) {
        _uiState.value = _uiState.value.copy(
            securityQuestion = securityQuestion,
            isSignUpEnabled = validateForm()
        )
    }

    private fun onSecurityAnswerChange(securityAnswer: String) {
        _uiState.value = _uiState.value.copy(
            answer = securityAnswer,
            isSignUpEnabled = validateForm()
        )
    }

    private fun onNameChange(newUserName: String) {
        _uiState.value = _uiState.value.copy(
            userName = newUserName,
            isSignUpEnabled = validateForm()
        )
    }

    private fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(
            email = newEmail,
            isSignUpEnabled = validateForm()
        )
    }

    private fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(
            password = newPassword,
            isSignUpEnabled = validateForm()
        )
    }

    private fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = newConfirmPassword,
            isSignUpEnabled = validateForm()
        )
    }

    private fun validateForm(): Boolean {
        return _uiState.value.userName.isNotBlank() &&
                _uiState.value.email.isNotBlank() &&
                _uiState.value.password.isNotBlank() &&
                _uiState.value.confirmPassword == _uiState.value.password &&
                _uiState.value.securityQuestion.isNotBlank() &&
                _uiState.value.answer.isNotBlank()
    }

    // TODO: Call signup use case
    private fun onSignUpClick() {
        if (validateForm()) {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Call signup use case (e.g., async process)
        }
    }
}
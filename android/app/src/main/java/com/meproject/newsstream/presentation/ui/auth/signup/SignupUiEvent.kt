package com.meproject.newsstream.presentation.ui.auth.signup

sealed class SignupUiEvent {
    data class NameChanged(val name: String) : SignupUiEvent()
    data class EmailChanged(val email: String) : SignupUiEvent()
    data class PasswordChanged(val password: String) : SignupUiEvent()
    data class SecurityQuestionChanged(val question: String) : SignupUiEvent()
    data class SecurityAnswerChanged(val answer: String) : SignupUiEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignupUiEvent()
    data object SignUpClick : SignupUiEvent()
    data object SignedUp : SignupUiEvent()
}
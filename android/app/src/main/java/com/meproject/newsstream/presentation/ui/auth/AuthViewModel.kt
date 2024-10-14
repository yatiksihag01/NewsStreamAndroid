package com.meproject.newsstream.presentation.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.use_case.GetAuthTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase
) : ViewModel() {

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

    fun onLoginClick() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        getAuthTokenUseCase(_uiState.value.email, _uiState.value.password).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
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
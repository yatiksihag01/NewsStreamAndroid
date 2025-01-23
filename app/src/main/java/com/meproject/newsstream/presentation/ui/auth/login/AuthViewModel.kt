package com.meproject.newsstream.presentation.ui.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.LoginDetails
import com.meproject.newsstream.domain.use_case.GetAccessTokenUseCase
import com.meproject.newsstream.domain.use_case.UserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userLoginUseCase: UserLoginUseCase,
    getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    val tokenFlow = getAccessTokenUseCase()

    fun handleEvent(uiEvent: LoginUiEvent) {
        when (uiEvent) {
            is LoginUiEvent.EmailChanged -> {
                onEmailChange(uiEvent.email)
            }
            is LoginUiEvent.PasswordChanged -> {
                onPasswordChange(uiEvent.password)
            }
            is LoginUiEvent.LoginClick -> {
                onLoginClick()
            }
            is LoginUiEvent.LoggedIn -> {
                _uiState.value = _uiState.value.copy(
                    navigateToHome = false
                )
            }
        }
    }

    private fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        _uiState.value = _uiState.value.copy(isLoginEnabled = isLoginEnabled())
    }

    private fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        _uiState.value = _uiState.value.copy(isLoginEnabled = isLoginEnabled())
    }

    private fun onLoginClick() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        _uiState.value = _uiState.value.copy(errorMessage = null)
        val loginDetails = LoginDetails(_uiState.value.email, _uiState.value.password)
        userLoginUseCase(loginDetails).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _uiState.value = _uiState.value.copy(navigateToHome = true)
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

    private fun isLoginEnabled(): Boolean {
        return _uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank()
    }
}
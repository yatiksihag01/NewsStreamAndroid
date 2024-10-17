package com.meproject.newsstream.presentation.ui.auth.signup

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meproject.newsstream.presentation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
object Signup : NavigationDestination

fun NavGraphBuilder.signupScreenDestination(
    onNavigationToLogin: () -> Unit,
    onNavigationToPreference: () -> Unit,
) {
    composable<Signup> {
        val viewModel: SignupViewModel = hiltViewModel()
        val uiState by viewModel.uiState
        SignUpScreen(
            uiState = uiState,
            uiEvent = viewModel::handleEvent,
            onNavigationToPreference = onNavigationToPreference,
            onNavigationToLogin = onNavigationToLogin
        )
    }
}
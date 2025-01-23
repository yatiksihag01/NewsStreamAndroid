package com.meproject.newsstream.presentation.ui.auth.login

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meproject.newsstream.presentation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
object Login : NavigationDestination

fun NavGraphBuilder.loginScreenDestination(
    onNavigationToHome: () -> Unit,
    onNavigationToSignup: () -> Unit,
    onNavigationToForgotPassword: () -> Unit,
) {
    composable<Login>(
        exitTransition = {
            fadeOut(
                animationSpec = tween(400, easing = EaseOut)
            )
        }
    ) {
        val viewModel: AuthViewModel = hiltViewModel()
        val uiState by viewModel.uiState
        LoginScreen(
            uiState = uiState,
            uiEvent = viewModel::handleEvent,
            onNavigationToHome = onNavigationToHome,
            onNavigationToSignup = onNavigationToSignup,
            onNavigationToForgotPassword = onNavigationToForgotPassword
        )
    }
}
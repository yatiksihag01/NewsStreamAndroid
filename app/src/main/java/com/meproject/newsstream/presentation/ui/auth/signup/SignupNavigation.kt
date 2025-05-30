package com.meproject.newsstream.presentation.ui.auth.signup

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
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
object Signup : NavigationDestination

fun NavGraphBuilder.signupScreenDestination(
    onNavigationToLogin: () -> Unit,
    onNavigationToPreference: () -> Unit,
) {
    composable<Signup>(enterTransition = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Up,
            animationSpec = tween(400, easing = EaseIn)
        )
    }, exitTransition = {
        fadeOut(
            animationSpec = tween(400, easing = EaseOut)
        ) + slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Down,
            animationSpec = tween(400, easing = EaseOut)
        )
    }) {
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
package com.meproject.newsstream.presentation.ui.auth.preference

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meproject.newsstream.presentation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
object Preference : NavigationDestination

fun NavGraphBuilder.preferenceScreenDestination(
    onNavigationToHome: () -> Unit
) {
    composable<Preference> {
        val viewModel: PreferenceViewModel = hiltViewModel()
        val uiState by viewModel.uiState
        PreferenceScreen(
            uiState = uiState,
            uiEvent = viewModel::handleEvent,
            onNavigationToHome = onNavigationToHome,
        )
    }
}
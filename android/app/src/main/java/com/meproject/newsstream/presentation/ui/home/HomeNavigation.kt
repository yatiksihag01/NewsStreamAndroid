package com.meproject.newsstream.presentation.ui.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meproject.newsstream.presentation.NavigationDestination
import com.meproject.newsstream.presentation.ui.auth.login.AuthViewModel
import kotlinx.serialization.Serializable

@Serializable
object Home : NavigationDestination

fun NavGraphBuilder.homeScreenDestination() {
    composable<Home> {
        val viewModel: AuthViewModel = hiltViewModel()
        HomeScreen()
    }
}
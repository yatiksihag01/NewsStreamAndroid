package com.meproject.newsstream.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Main : NavigationDestination

fun NavGraphBuilder.mainScreenDestination() {
    composable<Main> {
        MainScreen()
    }
}
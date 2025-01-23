package com.meproject.newsstream.presentation.ui.explore

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meproject.newsstream.presentation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
object Explore : NavigationDestination

fun NavGraphBuilder.exploreScreenDestination() {
    composable<Explore> {
        val viewModel: ExploreViewModel = hiltViewModel()
        ExploreScreen()
    }
}
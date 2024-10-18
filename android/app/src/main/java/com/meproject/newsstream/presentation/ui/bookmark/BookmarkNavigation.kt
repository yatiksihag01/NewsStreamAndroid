package com.meproject.newsstream.presentation.ui.bookmark

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meproject.newsstream.presentation.NavigationDestination
import com.meproject.newsstream.presentation.ui.auth.login.AuthViewModel
import com.meproject.newsstream.presentation.ui.explore.ExploreViewModel
import com.meproject.newsstream.presentation.ui.home.Home
import com.meproject.newsstream.presentation.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Bookmark : NavigationDestination

fun NavGraphBuilder.bookmarkScreenDestination() {
    composable<Home> {
        val viewModel: BookmarkViewModel = hiltViewModel()
        HomeScreen()
    }
}
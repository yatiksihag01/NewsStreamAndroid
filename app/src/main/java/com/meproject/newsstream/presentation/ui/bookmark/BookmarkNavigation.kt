package com.meproject.newsstream.presentation.ui.bookmark

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meproject.newsstream.presentation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
object Bookmark : NavigationDestination

fun NavGraphBuilder.bookmarkScreenDestination() {
    composable<Bookmark> {
        val viewModel: BookmarkViewModel = hiltViewModel()
        BookmarkScreen()
    }
}
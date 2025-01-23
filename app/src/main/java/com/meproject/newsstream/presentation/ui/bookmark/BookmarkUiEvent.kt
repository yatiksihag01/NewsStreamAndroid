package com.meproject.newsstream.presentation.ui.bookmark

sealed class BookmarkUiEvent {
    data object OnBookmarkClick : BookmarkUiEvent()
    data class OnBookmarkDelete(val bookmarkId: Int) : BookmarkUiEvent()
}
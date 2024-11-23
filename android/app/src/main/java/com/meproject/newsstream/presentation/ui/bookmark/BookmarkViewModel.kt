package com.meproject.newsstream.presentation.ui.bookmark

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.Bookmark
import com.meproject.newsstream.domain.model.BookmarkableContent
import com.meproject.newsstream.domain.use_case.DeleteBookmarkUseCase
import com.meproject.newsstream.domain.use_case.GetBookmarkPagesUseCase
import com.meproject.newsstream.domain.use_case.GetSummaryUseCase
import com.meproject.newsstream.domain.use_case.InsertBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    getBookmarksUseCase: GetBookmarkPagesUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val getSummaryUseCase: GetSummaryUseCase
) : ViewModel() {
    val flow = getBookmarksUseCase(PAGE_SIZE).cachedIn(viewModelScope)
    private val _summaryState = MutableStateFlow<Resource<String>>(Resource.Loading())
    val summaryResourceFlow: Flow<Resource<String>> = _summaryState

    fun getSummary(articleUrl: String) = viewModelScope.launch {
        getSummaryUseCase(articleUrl).collect { result ->
            _summaryState.value = result
        }
    }

    fun toggleBookmark(bookmark: Bookmark, isBookmarked: Boolean) = viewModelScope.launch {
        if (isBookmarked) {
            deleteBookmarkUseCase(bookmark.url)
        } else {
            insertBookmarkUseCase(BookmarkableContent(bookmark))
        }
    }
}
package com.meproject.newsstream.presentation.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.meproject.newsstream.common.Constants.ITEMS_PER_PAGE
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.Article
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
    val flow = getBookmarksUseCase(ITEMS_PER_PAGE).cachedIn(viewModelScope)
    private val _summaryState = MutableStateFlow<Resource<String>>(Resource.Loading())
    val summaryResourceFlow: Flow<Resource<String>> = _summaryState

    fun getSummary(articleUrl: String) = viewModelScope.launch {
        getSummaryUseCase(articleUrl).collect { result ->
            _summaryState.value = result
        }
    }

    fun toggleBookmark(article: Article, isBookmarked: Boolean) = viewModelScope.launch {
        if (isBookmarked) {
            deleteBookmarkUseCase(article.url)
        } else {
            insertBookmarkUseCase(article)
        }
    }
}
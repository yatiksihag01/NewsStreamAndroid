package com.meproject.newsstream.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.BookmarkableContent
import com.meproject.newsstream.domain.model.Trending
import com.meproject.newsstream.domain.use_case.DeleteBookmarkUseCase
import com.meproject.newsstream.domain.use_case.GetBreakingNewsUseCase
import com.meproject.newsstream.domain.use_case.GetSummaryUseCase
import com.meproject.newsstream.domain.use_case.GetTrendingTopicsUseCase
import com.meproject.newsstream.domain.use_case.InsertBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val getSummaryUseCase: GetSummaryUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    getTrendingNewsUseCase: GetTrendingTopicsUseCase,
) : ViewModel() {
    val trendingPageFlow = getTrendingNewsUseCase().cachedIn(viewModelScope)
    private val _summaryState = MutableStateFlow<Resource<String>>(Resource.Loading())
    val summaryResourceFlow: Flow<Resource<String>> = _summaryState

    fun getSummary(articleUrl: String) = viewModelScope.launch {
        getSummaryUseCase(articleUrl).collect { result ->
            _summaryState.value = result
        }
    }

    fun toggleBookmark(article: Trending) = viewModelScope.launch {
        if (article.isBookmarked) {
            deleteBookmarkUseCase(article.url)
        } else {
            insertBookmarkUseCase(
                BookmarkableContent(
                    content = article,
                )
            )
        }
    }
}
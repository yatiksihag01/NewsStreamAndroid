package com.meproject.newsstream.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.meproject.newsstream.domain.use_case.GetBreakingNewsUseCase
import com.meproject.newsstream.domain.use_case.GetTrendingTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    getTrendingNewsUseCase: GetTrendingTopicsUseCase
) : ViewModel() {
    val trendingPageFlow = getTrendingNewsUseCase().cachedIn(viewModelScope)
}
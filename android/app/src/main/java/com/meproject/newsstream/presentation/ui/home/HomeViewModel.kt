package com.meproject.newsstream.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.meproject.newsstream.domain.use_case.GetBreakingNewsUseCase
import com.meproject.newsstream.domain.use_case.GetTrendingTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val getTrendingNewsUseCase: GetTrendingTopicsUseCase
) : ViewModel() {

}
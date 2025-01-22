package com.meproject.newsstream.presentation.ui.explore

import androidx.lifecycle.ViewModel
import com.meproject.newsstream.domain.use_case.GetAllCategorizedArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getAllCategorizedArticlesUseCase: GetAllCategorizedArticlesUseCase
) : ViewModel()
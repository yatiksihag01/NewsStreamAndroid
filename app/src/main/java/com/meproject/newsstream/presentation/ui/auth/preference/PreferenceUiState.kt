package com.meproject.newsstream.presentation.ui.auth.preference

import com.meproject.newsstream.domain.model.Category

data class PreferenceUiState(
    val categories: List<Category> = emptyList(),
    val isFetching: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val navigateToHome: Boolean = false
)

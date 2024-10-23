package com.meproject.newsstream.presentation.ui.auth.preference

import com.meproject.newsstream.domain.model.Category

sealed class PreferenceUiEvent {
    data class CategorySelected(val category: Category) : PreferenceUiEvent()
    data object SavePreferences : PreferenceUiEvent()
    data object PreferencesSaved : PreferenceUiEvent()
}
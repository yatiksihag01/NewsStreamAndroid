package com.meproject.newsstream.presentation.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.Category
import com.meproject.newsstream.domain.use_case.GetCategoriesUseCase
import com.meproject.newsstream.domain.use_case.SaveSelectedCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenceViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val saveSelectedCategoriesUseCase: SaveSelectedCategoriesUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(PreferenceUiState())
    val uiState: State<PreferenceUiState> = _uiState

    init {
        viewModelScope.launch {
            fetchCategories()
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isFetching = true)
            getCategoriesUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isFetching = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            categories = result.data ?: emptyList(),
                            isFetching = false
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.message,
                            isFetching = false
                        )
                    }
                }
            }
        }
    }

    fun onCategorySelected(category: Category) {
        _uiState.value = _uiState.value.copy(
            categories = _uiState.value.categories.map {
                if (it.id == category.id) it.copy(isSelected = !it.isSelected) else it
            }
        )
    }

    fun savePreferences() {
        viewModelScope.launch {
            saveSelectedCategoriesUseCase(uiState.value.categories.filter { it.isSelected })
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(isSaving = false)
                        }
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                errorMessage = result.message,
                                isSaving = false
                            )
                        }
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(isSaving = true)
                        }
                    }
                }
        }
    }
}

package com.meproject.newsstream.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.SearchedArticle
import com.meproject.newsstream.domain.use_case.GetSearchedArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSearchedArticlesUseCase: GetSearchedArticlesUseCase
) : ViewModel() {

    private val _searchedArticlesState =
        MutableStateFlow<Resource<List<SearchedArticle>>>(Resource.Loading())
    private val searchedArticlesResourceFlow: Flow<Resource<List<SearchedArticle>>> =
        _searchedArticlesState

    fun searchNews(query: String) = viewModelScope.launch {
        getSearchedArticlesUseCase(query).collect { result ->
            _searchedArticlesState.value = result
        }
    }
}
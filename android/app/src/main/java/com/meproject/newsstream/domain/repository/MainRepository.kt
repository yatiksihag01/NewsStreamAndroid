package com.meproject.newsstream.domain.repository

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.Category
import com.meproject.newsstream.domain.model.SearchedArticle
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun searchNews(query: String): Flow<Resource<List<SearchedArticle>>>
    suspend fun saveSelectedCategories(categories: List<Category>){}
}

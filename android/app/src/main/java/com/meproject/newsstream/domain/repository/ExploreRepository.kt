package com.meproject.newsstream.domain.repository

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.CategoryNewsItem
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    fun getAllNews(): Flow<PagingData<CategoryNewsItem>>
}
package com.meproject.newsstream.domain.repository

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.domain.model.BreakingNews
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getTrendingNews(): Flow<PagingData<Article>>
    suspend fun getBreakingNews(size: Int): List<BreakingNews>
}
package com.meproject.newsstream.domain.repository

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.BreakingNews
import com.meproject.newsstream.domain.model.Trending
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getTrendingNews(): Flow<PagingData<Trending>>
    suspend fun getBreakingNews(size: Int): List<BreakingNews>
}
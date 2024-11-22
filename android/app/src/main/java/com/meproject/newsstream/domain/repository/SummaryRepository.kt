package com.meproject.newsstream.domain.repository

import com.meproject.newsstream.common.Resource
import kotlinx.coroutines.flow.Flow

interface SummaryRepository {
    suspend fun getSummary(articleUrl: String): Flow<Resource<String>>
}
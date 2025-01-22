package com.meproject.newsstream.domain.repository

import com.meproject.newsstream.domain.model.Article

interface ExploreRepository {
    suspend fun getAllArticles(): List<Article>
    suspend fun getCategorizedArticles(
        category: String,
        pageSize: Int? = null
    ): List<Article>
}
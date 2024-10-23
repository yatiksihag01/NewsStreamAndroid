package com.meproject.newsstream.domain.repository

import com.meproject.newsstream.domain.model.explore.CategorizedArticle

interface ExploreRepository {
    suspend fun getAllArticles(): List<CategorizedArticle>
    suspend fun getCategorizedArticles(
        category: String,
        pageSize: Int? = null
    ): List<CategorizedArticle>
}
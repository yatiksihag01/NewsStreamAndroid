package com.meproject.newsstream.data.repository

import com.meproject.newsstream.data.remote.api.NewsApi
import com.meproject.newsstream.domain.model.CategorizedArticle
import com.meproject.newsstream.domain.repository.ExploreRepository
import com.meproject.newsstream.data.mappers.toCategorizedArticle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExploreRepository {
    override suspend fun getAllArticles(): List<CategorizedArticle> = withContext(ioDispatcher) {
        newsApi.getAllArticles().map { it.toCategorizedArticle() }
    }

    override suspend fun getCategorizedArticles(
        category: String,
        pageSize: Int?
    ): List<CategorizedArticle> = withContext(ioDispatcher) {
        newsApi.getCategorizedArticles(category, pageSize).map { it.toCategorizedArticle() }
    }
}
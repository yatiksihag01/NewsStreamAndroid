package com.meproject.newsstream.data.remote.api

import com.meproject.newsstream.data.remote.dto.article.ArticleDto
import com.meproject.newsstream.data.remote.dto.breaking.BreakingNewsDto
import com.meproject.newsstream.data.remote.dto.summary.SummaryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("trending-topics/")
    suspend fun getTrendingArticles(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): List<ArticleDto>

    @GET("breaking-news")
    suspend fun getBreakingNews(
        @Query("size") size: Int
    ): List<BreakingNewsDto>

    @GET("everything")
    suspend fun getAllArticles(): List<ArticleDto>

    @GET("{category}")
    suspend fun getCategorizedArticles(
        @Path("category") category: String,
        @Query("pageSize") pageSize: Int? = null
    ): List<ArticleDto>

    @GET("summarize")
    suspend fun getSummary(
        @Query("article_url") articleUrl: String
    ): SummaryResponse
}
package com.meproject.newsstream.data.remote.api

import com.meproject.newsstream.data.remote.dto.breaking.BreakingNewsDto
import com.meproject.newsstream.data.remote.dto.explore.CategoryNewsItemDto
import com.meproject.newsstream.data.remote.dto.search.SearchedArticleDto
import com.meproject.newsstream.data.remote.dto.summary.SummaryResponse
import com.meproject.newsstream.data.remote.dto.trending.TrendingDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("trending-topics")
    suspend fun getTrendingArticles(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): List<TrendingDto>

    @GET("breaking-news")
    suspend fun getBreakingNews(
        @Query("size") size: Int
    ): List<BreakingNewsDto>

    @GET("{category}")
    suspend fun getCategorizedArticles(
        @Path("category") category: String,
        @Query("pageSize") pageSize: Int? = null
    ): List<CategoryNewsItemDto>

    @GET("categories")
    suspend fun getCategories(): List<String>

    @GET("all-news")
    suspend fun getAllNews(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): List<CategoryNewsItemDto>

    @GET("summarize")
    suspend fun getSummary(
        @Query("article_url") articleUrl: String
    ): SummaryResponse

    @GET("search")
    suspend fun search(
        @Query("query") query: String,
        @Query("page_size") pageSize: Int
    ): List<SearchedArticleDto>
}
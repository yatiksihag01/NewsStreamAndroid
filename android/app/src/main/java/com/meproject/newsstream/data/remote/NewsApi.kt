package com.meproject.newsstream.data.remote

import com.meproject.newsstream.data.remote.dto.trending.TrendingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("trending-topics")
    suspend fun getTrendingArticles(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<TrendingDto>
}
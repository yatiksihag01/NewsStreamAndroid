package com.meproject.newsstream.data.remote.dto.trending

data class TrendingDto(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceDto: SourceDto,
    val title: String,
    val url: String,
    val urlToImage: String,
    val sentiment: String
)
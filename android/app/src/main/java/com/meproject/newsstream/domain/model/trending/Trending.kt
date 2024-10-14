package com.meproject.newsstream.domain.model.trending

data class Trending(
    val url: String,
    val title: String,
    val publishedAt: String,
    val source: Source,
    val urlToImage: String,
    val sentiment: String
)

package com.meproject.newsstream.domain.model

data class CategoryNewsItem(
    val categoryName: String,
    val publishedAt: String,
    val sentiment: String,
    val source: String,
    val sourceImageUrl: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    var isBookmarked: Boolean
)

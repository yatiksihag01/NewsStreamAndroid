package com.meproject.newsstream.domain.model

data class CategorizedArticle(
    val url: String,
    val title: String,
    val publishedAt: String,
    val source: String,
    val sourceImageUrl: String? = null,
    val urlToImage: String,
    val sentiment: String,
    val category: String
)

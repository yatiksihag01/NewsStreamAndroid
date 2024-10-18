package com.meproject.newsstream.domain.model

data class Bookmark(
    val id: Int? = null,
    val url: String,
    val title: String,
    val urlToImage: String,
    val sourceImageUrl: String,
    val source: String,
    val publishedAt: String,
    val sentiment: String
)
package com.meproject.newsstream.domain.model.bookmark

data class Bookmark(
    val id: Int,
    val url: String,
    val title: String,
    val urlToImage: String,
    val sourceImageUrl: String,
    val source: String,
    val publishedAt: String,
)
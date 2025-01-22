package com.meproject.newsstream.domain.model

import com.google.gson.annotations.SerializedName

data class Article(
    val title: String,
    val url: String,
    val description: String?,
    @SerializedName("url_to_image")
    val urlToImage: String?,
    @SerializedName("published_at")
    val publishedAt: String,
    val source: Source,
    val sentiment: String,
    val category: String?,
    var isBookmarked: Boolean
)
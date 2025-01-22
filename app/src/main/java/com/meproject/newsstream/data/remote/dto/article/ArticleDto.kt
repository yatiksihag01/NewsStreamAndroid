package com.meproject.newsstream.data.remote.dto.article

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    val title: String,
    val url: String,
    val description: String?,
    @SerializedName("url_to_image")
    val urlToImage: String?,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("source")
    val sourceDto: SourceDto,
    val sentiment: String,
    val category: String?,
)

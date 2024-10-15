package com.meproject.newsstream.data.remote.dto.trending

import com.google.gson.annotations.SerializedName

data class TrendingDto(
    val author: String,
    val content: String,
    val description: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("source")
    val sourceDto: SourceDto,
    val title: String,
    val url: String,
    @SerializedName("url_to_image")
    val urlToImage: String,
    val sentiment: String
)
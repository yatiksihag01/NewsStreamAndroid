package com.meproject.newsstream.data.remote.dto.trending

import com.google.gson.annotations.SerializedName

data class TrendingDto(
    @SerializedName("published_at")
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    @SerializedName("url_to_image")
    val urlToImage: String,
    val sentiment: String
)
package com.meproject.newsstream.data.remote.dto.explore

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategorizedArticleDto(
    @SerializedName("published_at")
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    @SerializedName("url_to_image")
    val urlToImage: String,
    val sentiment: String,
    val category: String
)

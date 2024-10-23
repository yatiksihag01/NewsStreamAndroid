package com.meproject.newsstream.data.remote.dto.explore

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategorizedArticleDto(
    @SerializedName("name")
    val title: String,
    val url: String,
    @SerializedName("url_to_image")
    val urlToImage: String,
    val description: String,
    @SerializedName("provider")
    val providerDto: ProviderDto,
    @SerializedName("published_at")
    val publishedAt: String,
)

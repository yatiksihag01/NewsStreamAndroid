package com.meproject.newsstream.data.remote.dto.explore

import com.google.gson.annotations.SerializedName

data class CategoryNewsItemDto(
    @SerializedName("category_name")
    val categoryName: String,
    val id: Int,
    @SerializedName("published_at")
    val publishedAt: String,
    val sentiment: String,
    val source: String,
    @SerializedName("source_image_url")
    val sourceImageUrl: String,
    val title: String,
    val url: String,
    @SerializedName("url_to_image")
    val urlToImage: String
)
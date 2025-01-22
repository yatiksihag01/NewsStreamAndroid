package com.meproject.newsstream.data.remote.dto.breaking

import com.google.gson.annotations.SerializedName

data class BreakingNewsDto(
    val title: String,
    val url: String,
    @SerializedName("url_to_image")
    val urlToImage: String
)
package com.meproject.newsstream.data.remote.dto.search

import com.google.gson.annotations.SerializedName

data class SearchedArticleDto(
    val title: String,
    val url: String,
    @SerializedName("url_to_image")
    val urlToImage: String,
)

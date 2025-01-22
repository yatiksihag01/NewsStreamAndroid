package com.meproject.newsstream.data.remote.dto.article

import com.google.gson.annotations.SerializedName

data class SourceDto(
    val name: String,
    @SerializedName("source_image_url")
    val imageUrl: String?
)

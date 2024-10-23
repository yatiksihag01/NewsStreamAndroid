package com.meproject.newsstream.data.remote.dto.explore

import androidx.annotation.Keep

@Keep
data class ProviderDto(
    val name: String,
    val urlToImage: String
)

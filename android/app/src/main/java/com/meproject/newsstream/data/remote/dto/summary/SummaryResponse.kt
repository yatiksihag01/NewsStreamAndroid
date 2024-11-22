package com.meproject.newsstream.data.remote.dto.summary

import com.google.gson.annotations.SerializedName

data class SummaryResponse (
    @SerializedName("article_url")
    val articleUrl: String,
    val summary: String
)
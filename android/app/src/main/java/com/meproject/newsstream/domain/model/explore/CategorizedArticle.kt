package com.meproject.newsstream.domain.model.explore

import android.icu.text.CaseMap.Title

data class CategorizedArticle(
    val title: String,
    val url: String,
    val urlToImage: String,
    val provider: Provider,
    val publishedAt: String,
)

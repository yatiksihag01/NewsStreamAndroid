package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.remote.dto.explore.CategorizedArticleDto
import com.meproject.newsstream.domain.model.CategorizedArticle

fun CategorizedArticleDto.toCategorizedArticle(): CategorizedArticle =
    CategorizedArticle(
        url = url,
        title = title,
        publishedAt = publishedAt,
        source = source,
        urlToImage = urlToImage,
        sentiment = sentiment,
        category = category
    )
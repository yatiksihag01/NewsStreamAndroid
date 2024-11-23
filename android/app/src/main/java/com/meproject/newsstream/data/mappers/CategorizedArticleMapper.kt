package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.local.bookmark.BookmarkEntity
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

fun CategorizedArticle.toBookmarkEntity(): BookmarkEntity =
    BookmarkEntity(
        url = url,
        title = title,
        urlToImage = urlToImage,
        sourceImageUrl = sourceImageUrl,
        source = source,
        publishedAt = publishedAt,
        sentiment = sentiment
    )
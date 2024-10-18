package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.local.bookmark.BookmarkEntity
import com.meproject.newsstream.domain.model.Bookmark
import com.meproject.newsstream.domain.model.CategorizedArticle
import com.meproject.newsstream.domain.model.Trending

fun BookmarkEntity.toBookmark(): Bookmark =
     Bookmark(
        id = id,
        url = url,
        title = title,
        urlToImage = urlToImage,
        sourceImageUrl = sourceImageUrl,
        source = source,
        publishedAt = publishedAt
    )

fun Bookmark.toBookmarkEntity(): BookmarkEntity =
    BookmarkEntity(
        id = id ?: 0,
        url = url,
        title = title,
        urlToImage = urlToImage,
        sourceImageUrl = sourceImageUrl,source = source,
        publishedAt = publishedAt
    )

fun Trending.toBookmark(): Bookmark =
    Bookmark(
        url = url,
        urlToImage = urlToImage,
        title = title,
        sourceImageUrl = "",
        source = source,
        publishedAt = publishedAt
    )

fun CategorizedArticle.toBookmark(): Bookmark =
    Bookmark(
        url = url,
        urlToImage = urlToImage,
        title = title,
        sourceImageUrl = "",
        source = source,
        publishedAt = publishedAt
    )

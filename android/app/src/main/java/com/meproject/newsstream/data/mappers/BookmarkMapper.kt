package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.local.bookmark.BookmarkEntity
import com.meproject.newsstream.domain.model.bookmark.Bookmark

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
        id = id,
        url = url,
        title = title,
        urlToImage = urlToImage,
        sourceImageUrl = sourceImageUrl,
        source = source,
        publishedAt = publishedAt
    )
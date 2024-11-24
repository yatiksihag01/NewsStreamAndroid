package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.local.bookmark.BookmarkEntity
import com.meproject.newsstream.data.local.explore.AllNewsItemEntity
import com.meproject.newsstream.data.remote.dto.explore.CategoryNewsItemDto
import com.meproject.newsstream.domain.model.CategoryNewsItem

fun CategoryNewsItemDto.allNewsItemEntity(index: Int): AllNewsItemEntity =
    AllNewsItemEntity(
        url = url,
        categoryName = categoryName,
        index = index,
        publishedAt = publishedAt,
        sentiment = sentiment,
        source = source,
        sourceImageUrl = sourceImageUrl,
        title = title,
        urlToImage = urlToImage
    )

fun AllNewsItemEntity.toCategoryNewsItem(isBookmarked: Boolean): CategoryNewsItem =
    CategoryNewsItem(
        url = url,
        categoryName = categoryName,
        publishedAt = publishedAt,
        sentiment = sentiment,
        source = source,
        sourceImageUrl = sourceImageUrl,
        title = title,
        urlToImage = urlToImage,
        isBookmarked = isBookmarked
    )

fun CategoryNewsItem.toBookmarkEntity(): BookmarkEntity =
    BookmarkEntity(
        url = url,
        title = title,
        urlToImage = urlToImage,
        sourceImageUrl = sourceImageUrl,
        source = source,
        publishedAt = publishedAt,
        sentiment = sentiment
    )
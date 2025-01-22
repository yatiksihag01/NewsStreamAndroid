package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.local.bookmark.BookmarkEntity
import com.meproject.newsstream.data.local.trending.TrendingEntity
import com.meproject.newsstream.data.remote.dto.article.ArticleDto
import com.meproject.newsstream.data.remote.dto.article.SourceDto
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.domain.model.Source

fun ArticleDto.toArticle() = Article(
    title = title,
    url = url,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    source = sourceDto.toSource(),
    sentiment = sentiment,
    category = category,
    isBookmarked = false
)

fun ArticleDto.toTrendingEntity(index: Int) = TrendingEntity(
    index = index,
    title = title,
    url = url,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    source = sourceDto,
    sentiment = sentiment
)

fun TrendingEntity.toArticle(isBookmarked: Boolean) = Article(
    title = title,
    url = url,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    source = source.toSource(),
    sentiment = sentiment,
    category = null,
    isBookmarked = isBookmarked
)

fun Article.toBookmarkEntity() = BookmarkEntity(
    title = title,
    url = url,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    source = source.toSourceDto(),
    sentiment = sentiment,
    category = category
)

fun BookmarkEntity.toArticle() = Article(
    title = title,
    url = url,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    source = source.toSource(),
    sentiment = sentiment,
    category = category,
    isBookmarked = true
)

private fun SourceDto.toSource() = Source(
    name = name, imageUrl = imageUrl
)

private fun Source.toSourceDto() = SourceDto(
    name = name, imageUrl = imageUrl
)
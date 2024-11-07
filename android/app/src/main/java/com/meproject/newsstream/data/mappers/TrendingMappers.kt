package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.local.trending.TrendingEntity
import com.meproject.newsstream.data.remote.dto.trending.TrendingDto
import com.meproject.newsstream.domain.model.Trending

fun TrendingDto.toTrendingEntity(index: Int): TrendingEntity =
    TrendingEntity(
        index = index,
        url = url,
        publishedAt = publishedAt,
        source = source,
        title = title,
        urlToImage = urlToImage,
        sentiment = sentiment
    )

fun TrendingEntity.toTrending(): Trending =
    Trending(
        url = url,
        publishedAt = publishedAt,
        source = source,
        title = title,
        urlToImage = urlToImage,
        sentiment = sentiment
    )
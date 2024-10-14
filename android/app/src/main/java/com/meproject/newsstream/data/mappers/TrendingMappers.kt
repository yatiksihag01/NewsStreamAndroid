package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.local.trending.SourceEntity
import com.meproject.newsstream.data.local.trending.TrendingEntity
import com.meproject.newsstream.data.remote.dto.trending.SourceDto
import com.meproject.newsstream.data.remote.dto.trending.TrendingDto
import com.meproject.newsstream.domain.model.trending.Source
import com.meproject.newsstream.domain.model.trending.Trending

fun TrendingDto.toTrendingEntity(): TrendingEntity =
    TrendingEntity(
        url = url,
        publishedAt = publishedAt,
        sourceEntity = sourceDto.toSourceEntity(),
        title = title,
        urlToImage = urlToImage,
        sentiment = sentiment
    )

fun SourceDto.toSourceEntity(): SourceEntity =
    SourceEntity(
        id = id,
        name = name
    )

fun TrendingEntity.toTrending(): Trending =
    Trending(
        url = url,
        publishedAt = publishedAt,
        source = sourceEntity.toSource(),
        title = title,
        urlToImage = urlToImage,
        sentiment = sentiment
    )

fun SourceEntity.toSource(): Source =
    Source(
        id = id,
        name = name
    )
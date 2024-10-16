package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.remote.dto.explore.CategorizedArticleDto
import com.meproject.newsstream.data.remote.dto.explore.ProviderDto
import com.meproject.newsstream.domain.model.explore.CategorizedArticle
import com.meproject.newsstream.domain.model.explore.Provider

fun CategorizedArticleDto.toCategorizedArticle(): CategorizedArticle =
    CategorizedArticle(
        title = title,
        url = url,
        urlToImage = urlToImage,
        provider = providerDto.toProvider(),
        publishedAt = publishedAt
    )

private fun ProviderDto.toProvider(): Provider =
    Provider(
        name = name,
        urlToImage = urlToImage
    )
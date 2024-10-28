package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.remote.dto.breaking.BreakingNewsDto
import com.meproject.newsstream.domain.model.BreakingNews

fun BreakingNewsDto.toBreakingNews() =
    BreakingNews(
        title = title,
        url = url,
        urlToImage = urlToImage
    )
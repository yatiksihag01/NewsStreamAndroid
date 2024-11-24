package com.meproject.newsstream.data.mappers

import com.meproject.newsstream.data.remote.dto.search.SearchedArticleDto
import com.meproject.newsstream.domain.model.SearchedArticle

fun SearchedArticleDto.toSearchedArticle(): SearchedArticle =
    SearchedArticle(
        title = title,
        url = url,
        urlToImage = urlToImage
    )
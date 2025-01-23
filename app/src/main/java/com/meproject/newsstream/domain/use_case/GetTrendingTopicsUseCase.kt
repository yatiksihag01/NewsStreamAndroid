package com.meproject.newsstream.domain.use_case

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingTopicsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return homeRepository.getTrendingNews()
    }
}
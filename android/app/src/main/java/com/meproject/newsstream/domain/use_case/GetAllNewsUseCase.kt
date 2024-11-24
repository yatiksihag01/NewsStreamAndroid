package com.meproject.newsstream.domain.use_case

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.CategoryNewsItem
import com.meproject.newsstream.domain.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor (
    private val exploreRepository: ExploreRepository
) {
    operator fun invoke(): Flow<PagingData<CategoryNewsItem>> {
        return exploreRepository.getAllNews()
    }
}
package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.repository.SummaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSummaryUseCase @Inject constructor(
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(articleUrl: String): Flow<Resource<String>> {
        return summaryRepository.getSummary(articleUrl)
    }
}
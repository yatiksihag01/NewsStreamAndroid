package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.SearchedArticle
import com.meproject.newsstream.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get searched articles based on query.
 * @param query The query to search for.
 * @return A flow of [Resource] containing a list of [SearchedArticle].
 */
class GetSearchedArticlesUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<SearchedArticle>>> {
        return mainRepository.searchNews(query)
    }
}
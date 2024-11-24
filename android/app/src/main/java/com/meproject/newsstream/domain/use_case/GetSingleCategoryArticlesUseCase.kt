package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.repository.ExploreRepository
import javax.inject.Inject

class GetSingleCategoryArticlesUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {

}
package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.Category
import com.meproject.newsstream.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Category>>> = flow {
        try {
            emit(Resource.Loading())
            val categories = categoryRepository.getCategories()
            emit (Resource.Success(categories))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
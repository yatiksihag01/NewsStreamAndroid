package com.meproject.newsstream.data.repository

import com.meproject.newsstream.domain.model.Category
import com.meproject.newsstream.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun saveSelectedCategories(categories: List<Category>) {
        TODO("Not yet implemented")
    }
}
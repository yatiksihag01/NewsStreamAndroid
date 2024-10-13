package com.meproject.newsstream.domain.repository

import com.meproject.newsstream.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
    suspend fun saveSelectedCategories(categories: List<Category>)
}

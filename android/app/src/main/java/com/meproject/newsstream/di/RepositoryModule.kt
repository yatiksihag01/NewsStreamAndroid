package com.meproject.newsstream.di

import com.meproject.newsstream.data.repository.CategoryRepositoryImpl
import com.meproject.newsstream.data.repository.HomeRepositoryImpl
import com.meproject.newsstream.data.repository.LoginRepositoryImpl
import com.meproject.newsstream.domain.repository.CategoryRepository
import com.meproject.newsstream.domain.repository.HomeRepository
import com.meproject.newsstream.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository


}
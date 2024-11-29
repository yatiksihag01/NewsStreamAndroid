package com.meproject.newsstream.di

import com.meproject.newsstream.data.repository.BookmarkRepositoryImpl
import com.meproject.newsstream.data.repository.CategoryRepositoryImpl
import com.meproject.newsstream.data.repository.ExploreRepositoryImpl
import com.meproject.newsstream.data.repository.HomeRepositoryImpl
import com.meproject.newsstream.data.repository.LoginRepositoryImpl
import com.meproject.newsstream.data.repository.SummaryRepositoryImpl
import com.meproject.newsstream.domain.repository.BookmarkRepository
import com.meproject.newsstream.domain.repository.CategoryRepository
import com.meproject.newsstream.domain.repository.ExploreRepository
import com.meproject.newsstream.domain.repository.HomeRepository
import com.meproject.newsstream.domain.repository.LoginRepository
import com.meproject.newsstream.domain.repository.SummaryRepository
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

    @Binds
    @Singleton
    abstract fun bindExploreRepository(
        exploreRepositoryImpl: ExploreRepositoryImpl
    ): ExploreRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository

    @Binds
    @Singleton
    abstract fun bindSummaryRepository(
        summaryRepositoryImpl: SummaryRepositoryImpl
    ): SummaryRepository

}
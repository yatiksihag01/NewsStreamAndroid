package com.meproject.newsstream.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.meproject.newsstream.common.Constants.ITEMS_PER_PAGE
import com.meproject.newsstream.data.local.NewsDatabase
import com.meproject.newsstream.data.local.bookmark.BookmarkDao
import com.meproject.newsstream.data.local.trending.TrendingDao
import com.meproject.newsstream.data.remote.TrendingRemoteMediator
import com.meproject.newsstream.data.remote.api.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        NewsDatabase::class.java,
        "barcode_database"
    ).build()

    @Provides
    @Singleton
    @OptIn(ExperimentalPagingApi::class)
    fun provideTrendingPager(newsApi: NewsApi, newsDatabase: NewsDatabase) =
        Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = TrendingRemoteMediator(
                newsApi = newsApi,
                newsDatabase = newsDatabase
            ),
            pagingSourceFactory = {
                newsDatabase.trendingDao().pagingSource()
            }
        )

    @Provides
    @Singleton
    fun provideTrendingDao(database: NewsDatabase): TrendingDao = database.trendingDao()

    @Provides
    @Singleton
    fun provideBookmarkDao(database: NewsDatabase): BookmarkDao = database.bookmarkDao()

    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}
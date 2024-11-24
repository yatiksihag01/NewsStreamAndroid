package com.meproject.newsstream.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.meproject.newsstream.data.local.bookmark.BookmarkDao
import com.meproject.newsstream.data.local.explore.AllNewsItemEntity
import com.meproject.newsstream.data.mappers.toCategoryNewsItem
import com.meproject.newsstream.data.remote.api.NewsApi
import com.meproject.newsstream.domain.repository.ExploreRepository
import com.meproject.newsstream.domain.model.CategoryNewsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val bookmarkDao: BookmarkDao,
    private val allNewsItemPager: Pager<Int, AllNewsItemEntity>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExploreRepository {
    override fun getAllNews(): Flow<PagingData<CategoryNewsItem>> {
        return allNewsItemPager.flow.map { pagingData ->
            val articleUrls = bookmarkDao.getAllBookmarkUrls().toSet()
            pagingData.map { allNewsItemEntity ->
                val isBookmarked = articleUrls.contains(allNewsItemEntity.url)
                allNewsItemEntity.toCategoryNewsItem(isBookmarked = isBookmarked)
            }
        }
    }
}
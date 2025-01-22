package com.meproject.newsstream.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.meproject.newsstream.data.local.bookmark.BookmarkDao
import com.meproject.newsstream.data.local.trending.TrendingEntity
import com.meproject.newsstream.data.mappers.toArticle
import com.meproject.newsstream.data.mappers.toBreakingNews
import com.meproject.newsstream.data.remote.api.NewsApi
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.domain.model.BreakingNews
import com.meproject.newsstream.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val trendingPager: Pager<Int, TrendingEntity>,
    private val bookmarkDao: BookmarkDao,
    private val newsApi: NewsApi
) : HomeRepository {
    override fun getTrendingNews(): Flow<PagingData<Article>> {
        return trendingPager.flow.map { pagingData ->
            val articleUrls = bookmarkDao.getAllBookmarkUrls().toSet()
            pagingData.map { trendingEntity ->
                val isBookmarked = articleUrls.contains(trendingEntity.url)
                trendingEntity.toArticle(isBookmarked = isBookmarked)
            }
        }
    }

    override suspend fun getBreakingNews(size: Int): List<BreakingNews> {
        return newsApi.getBreakingNews(size).map { it.toBreakingNews() }
    }
}
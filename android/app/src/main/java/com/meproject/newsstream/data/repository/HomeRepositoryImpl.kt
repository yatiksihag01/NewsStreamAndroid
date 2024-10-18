package com.meproject.newsstream.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.meproject.newsstream.data.local.trending.TrendingEntity
import com.meproject.newsstream.data.mappers.toBreakingNews
import com.meproject.newsstream.data.mappers.toTrending
import com.meproject.newsstream.data.remote.NewsApi
import com.meproject.newsstream.domain.model.BreakingNews
import com.meproject.newsstream.domain.model.Trending
import com.meproject.newsstream.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val trendingPager: Pager<Int, TrendingEntity>,
    private val newsApi: NewsApi
) : HomeRepository {
    override fun getTrendingNews(): Flow<PagingData<Trending>> {
        return trendingPager.flow.map { pagingData ->
            pagingData.map { trendingEntity ->
                trendingEntity.toTrending()
            }
        }
    }

    override suspend fun getBreakingNews(size: Int): List<BreakingNews> {
        return newsApi.getBreakingNews(size).map { it.toBreakingNews() }
    }
}
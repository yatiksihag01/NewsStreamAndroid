package com.meproject.newsstream.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.meproject.newsstream.data.local.trending.TrendingEntity
import com.meproject.newsstream.data.mappers.toTrending
import com.meproject.newsstream.domain.model.trending.Trending
import com.meproject.newsstream.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val trendingPager: Pager<Int, TrendingEntity>,
) : HomeRepository {
    override fun getTrendingNews(): Flow<PagingData<Trending>> {
        return trendingPager.flow.map { pagingData ->
            pagingData.map { trendingEntity ->
                trendingEntity.toTrending()
            }
        }
    }
}
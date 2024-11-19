package com.meproject.newsstream.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.meproject.newsstream.data.local.NewsDatabase
import com.meproject.newsstream.data.local.trending.TrendingEntity
import com.meproject.newsstream.data.mappers.toTrendingEntity
import com.meproject.newsstream.data.remote.api.NewsApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TrendingRemoteMediator(
    private val newsApi: NewsApi, private val newsDatabase: NewsDatabase
) : RemoteMediator<Int, TrendingEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, TrendingEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                // We are only appending pages
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    // If lastItem is null it means no items were loaded after the initial REFRESH
                    // and there are no more items to load.
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    (lastItem.index / state.config.pageSize) + 1
                }
            }
            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val trendingArticles = newsApi.getTrendingArticles(loadKey, state.config.pageSize)
            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDatabase.trendingDao().clearAll()
                }
                val lastItemIndex = state.lastItemOrNull()?.index ?: 0
                val trendingEntities = trendingArticles.mapIndexed { index, trendingArticle ->
                    trendingArticle.toTrendingEntity(index + lastItemIndex + 1)
                }
                newsDatabase.trendingDao().upsertAll(trendingEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = trendingArticles.size < state.config.pageSize
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}

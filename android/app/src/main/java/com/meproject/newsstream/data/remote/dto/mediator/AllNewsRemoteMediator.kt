package com.meproject.newsstream.data.remote.dto.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.meproject.newsstream.data.local.NewsDatabase
import com.meproject.newsstream.data.local.explore.AllNewsItemEntity
import com.meproject.newsstream.data.mappers.allNewsItemEntity
import com.meproject.newsstream.data.remote.api.NewsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AllNewsRemoteMediator @Inject constructor(
    private val newsApi: NewsApi, private val newsDatabase: NewsDatabase
)  : RemoteMediator<Int, AllNewsItemEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, AllNewsItemEntity>
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
            val allNewsItems = newsApi.getAllNews(
                page = loadKey,
                pageSize = state.config.pageSize
            )
            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDatabase.allNewsItemDao().clearAll()
                }
                val lastItemIndex = state.lastItemOrNull()?.index ?: 0
                val allNewsItemEntities = allNewsItems.mapIndexed { index, allNewsItem ->
                    allNewsItem.allNewsItemEntity(index + lastItemIndex + 1)
                }
                newsDatabase.allNewsItemDao().upsertAll(allNewsItemEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = allNewsItems.size < state.config.pageSize
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
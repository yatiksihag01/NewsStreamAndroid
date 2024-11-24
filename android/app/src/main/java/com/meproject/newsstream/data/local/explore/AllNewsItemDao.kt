package com.meproject.newsstream.data.local.explore

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AllNewsItemDao {

    @Upsert
    suspend fun upsertAll(allNewsEntities: List<AllNewsItemEntity>)

    @Query("SELECT * FROM all_news_item")
    fun pagingSource(): PagingSource<Int, AllNewsItemEntity>

    @Query("DELETE FROM all_news_item")
    suspend fun clearAll()

}
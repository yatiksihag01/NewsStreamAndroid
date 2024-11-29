package com.meproject.newsstream.data.local.trending

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface TrendingDao {

    @Upsert
    suspend fun upsertAll(trendingEntities: List<TrendingEntity>)

    @Query("SELECT * FROM trending_articles")
    fun pagingSource(): PagingSource<Int, TrendingEntity>

    @Query("DELETE FROM trending_articles")
    suspend fun clearAll()

}
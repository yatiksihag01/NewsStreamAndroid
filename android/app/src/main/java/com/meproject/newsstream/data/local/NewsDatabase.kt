package com.meproject.newsstream.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meproject.newsstream.data.local.bookmark.BookmarkDao
import com.meproject.newsstream.data.local.bookmark.BookmarkEntity
import com.meproject.newsstream.data.local.trending.TrendingDao
import com.meproject.newsstream.data.local.trending.TrendingEntity

@Database(
    entities = [TrendingEntity::class, BookmarkEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun trendingDao(): TrendingDao
    abstract fun bookmarkDao(): BookmarkDao
}
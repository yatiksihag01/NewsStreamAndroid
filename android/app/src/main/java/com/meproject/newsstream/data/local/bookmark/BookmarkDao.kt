package com.meproject.newsstream.data.local.bookmark

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): List<BookmarkEntity>

    @Query("SELECT * FROM bookmarks LIMIT :limit OFFSET :offset")
    suspend fun getBookmarkPages(limit: Int, offset: Int): List<BookmarkEntity>

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)
}
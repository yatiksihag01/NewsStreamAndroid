package com.meproject.newsstream.domain.repository

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun insertBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmarkId: Int)
    suspend fun undoDeletion(bookmark: Bookmark){}
    suspend fun getAllBookmarks(): List<Bookmark> = emptyList()
    fun getPagingDataStream(pageSize: Int): Flow<PagingData<Bookmark>>
}
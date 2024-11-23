package com.meproject.newsstream.domain.repository

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.Bookmark
import com.meproject.newsstream.domain.model.BookmarkableContent
import com.meproject.newsstream.domain.model.CategorizedArticle
import com.meproject.newsstream.domain.model.Trending
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    /**
     * Inserts a bookmark into the database.
     * @throws IllegalArgumentException If [BookmarkableContent.content] is not one of [Trending],
     * [CategorizedArticle], or [Bookmark]
     */
    suspend fun insertBookmark(content: BookmarkableContent)
    suspend fun deleteBookmark(bookmarkUrl: String)
    suspend fun undoDeletion(bookmark: Bookmark){}
    suspend fun getAllBookmarks(): List<Bookmark> = emptyList()
    fun getPagingDataStream(pageSize: Int): Flow<PagingData<Bookmark>>
}
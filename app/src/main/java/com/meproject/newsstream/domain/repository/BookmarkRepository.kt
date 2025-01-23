package com.meproject.newsstream.domain.repository

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun insertBookmark(article: Article)
    suspend fun deleteBookmark(bookmarkUrl: String)
    suspend fun undoDeletion(article: Article) {}
    suspend fun getAllBookmarks(): List<Article> = emptyList()
    fun getPagingDataStream(pageSize: Int): Flow<PagingData<Article>>
}
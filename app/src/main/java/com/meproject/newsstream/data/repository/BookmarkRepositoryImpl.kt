package com.meproject.newsstream.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.meproject.newsstream.data.local.bookmark.BookmarkDao
import com.meproject.newsstream.data.local.bookmark.BookmarkPagingSource
import com.meproject.newsstream.data.mappers.toArticle
import com.meproject.newsstream.data.mappers.toBookmarkEntity
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.domain.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookmarkRepository {
    override suspend fun insertBookmark(article: Article) = withContext(ioDispatcher) {
        bookmarkDao.insertBookmark(article.toBookmarkEntity())
    }

    override suspend fun deleteBookmark(bookmarkUrl: String) = withContext(ioDispatcher) {
        bookmarkDao.deleteBookmark(bookmarkUrl)
    }

    override fun getPagingDataStream(pageSize: Int): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { BookmarkPagingSource(bookmarkDao, pageSize) }
        ).flow.map { pagingData ->
            pagingData.map { bookmarkEntity ->
                bookmarkEntity.toArticle()
            }
        }
    }
}
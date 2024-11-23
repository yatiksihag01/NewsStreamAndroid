package com.meproject.newsstream.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.meproject.newsstream.data.local.bookmark.BookmarkDao
import com.meproject.newsstream.data.local.bookmark.BookmarkPagingSource
import com.meproject.newsstream.data.mappers.toBookmark
import com.meproject.newsstream.data.mappers.toBookmarkEntity
import com.meproject.newsstream.domain.model.Bookmark
import com.meproject.newsstream.domain.model.BookmarkableContent
import com.meproject.newsstream.domain.model.CategorizedArticle
import com.meproject.newsstream.domain.model.Trending
import com.meproject.newsstream.domain.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor (
    private val bookmarkDao: BookmarkDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookmarkRepository {
    override suspend fun insertBookmark(content: BookmarkableContent) = withContext(ioDispatcher) {
        when (content.content) {
            is Trending -> {
                bookmarkDao.insertBookmark(content.content.toBookmarkEntity())
            }

            is CategorizedArticle -> {
                bookmarkDao.insertBookmark(content.content.toBookmarkEntity())
            }

            is Bookmark -> {
                bookmarkDao.insertBookmark(content.content.toBookmarkEntity())
            }

            else -> throw IllegalArgumentException("Invalid content type")
        }
    }

    override suspend fun deleteBookmark(bookmarkUrl: String) = withContext(ioDispatcher) {
        bookmarkDao.deleteBookmark(bookmarkUrl)
    }

    override fun getPagingDataStream(pageSize: Int): Flow<PagingData<Bookmark>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { BookmarkPagingSource(bookmarkDao, pageSize) }
        ).flow.map { pagingData ->
            pagingData.map {
                bookmarkEntity -> bookmarkEntity.toBookmark()
            }
        }
    }
}
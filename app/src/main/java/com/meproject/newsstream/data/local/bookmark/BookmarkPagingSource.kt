package com.meproject.newsstream.data.local.bookmark

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BookmarkPagingSource (
    private val bookmarkDao: BookmarkDao,
    private val pageSize: Int
) : PagingSource<Int, BookmarkEntity>() {

    /**
     * prevKey == null -> anchorPage is the first page.
     *
     * nextKey == null -> anchorPage is the last page.
     *
     * Both prevKey and nextKey are null -> anchorPage is the initial page, so return null.
     */
    override fun getRefreshKey(state: PagingState<Int, BookmarkEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookmarkEntity> {
        return try {
            val currentPageNumber = params.key ?: 0
            val bookmarkDetailsList =
                bookmarkDao.getBookmarkPages(pageSize, currentPageNumber * pageSize)
            val prevKey = if (currentPageNumber > 0) currentPageNumber - 1 else null
            val nextKey = if (bookmarkDetailsList.isNotEmpty()) currentPageNumber + 1 else null

            LoadResult.Page(
                data = bookmarkDetailsList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
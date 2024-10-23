package com.meproject.newsstream.domain.use_case

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.bookmark.Bookmark
import com.meproject.newsstream.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetBookmarkPagesUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Bookmark>> = flow {
        bookmarkRepository.getPagingDataStream(pageSize)
    }
}
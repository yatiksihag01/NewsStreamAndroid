package com.meproject.newsstream.domain.use_case

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.bookmark.Bookmark
import com.meproject.newsstream.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarkPagesUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Bookmark>> =
        bookmarkRepository.getPagingDataStream(pageSize)
}
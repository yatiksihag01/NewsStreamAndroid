package com.meproject.newsstream.domain.use_case

import androidx.paging.PagingData
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkPagesUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Article>> =
        bookmarkRepository.getPagingDataStream(pageSize)
}
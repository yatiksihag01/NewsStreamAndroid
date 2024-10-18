package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.repository.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor (
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(bookmarkId: Int) {
        bookmarkRepository.deleteBookmark(bookmarkId)
    }
}
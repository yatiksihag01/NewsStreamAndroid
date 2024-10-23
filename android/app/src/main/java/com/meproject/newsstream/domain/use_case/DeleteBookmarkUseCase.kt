package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.model.bookmark.Bookmark
import com.meproject.newsstream.domain.repository.BookmarkRepository

class DeleteBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) {
        bookmarkRepository.deleteBookmark(bookmark)
    }
}
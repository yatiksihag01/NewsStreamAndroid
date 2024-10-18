package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.model.Bookmark
import com.meproject.newsstream.domain.repository.BookmarkRepository

class InsertBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) {
        bookmarkRepository.insertBookmark(bookmark)
    }
}
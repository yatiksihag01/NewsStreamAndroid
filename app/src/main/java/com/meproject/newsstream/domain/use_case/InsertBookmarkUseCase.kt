package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.domain.repository.BookmarkRepository
import javax.inject.Inject

/**
 * Inserts a bookmark into the database.
 */
class InsertBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(article: Article) {
        bookmarkRepository.insertBookmark(article)
    }
}
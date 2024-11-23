package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.model.Bookmark
import com.meproject.newsstream.domain.model.BookmarkableContent
import com.meproject.newsstream.domain.model.CategorizedArticle
import com.meproject.newsstream.domain.model.Trending
import com.meproject.newsstream.domain.repository.BookmarkRepository
import javax.inject.Inject

/**
 * Inserts a bookmark into the database.
 *
 * @throws IllegalArgumentException If [BookmarkableContent.content] is not one of [Trending],
 * [CategorizedArticle], or [Bookmark]
 */
class InsertBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(content: BookmarkableContent) {
        bookmarkRepository.insertBookmark(content)
    }
}
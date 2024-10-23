package com.meproject.newsstream.presentation.ui.bookmark

import com.meproject.newsstream.domain.use_case.DeleteBookmarkUseCase
import com.meproject.newsstream.domain.use_case.GetBookmarkPagesUseCase
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarkPagesUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) {

}
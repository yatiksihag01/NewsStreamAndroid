package com.meproject.newsstream.presentation.ui.bookmark

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.meproject.newsstream.domain.use_case.DeleteBookmarkUseCase
import com.meproject.newsstream.domain.use_case.GetBookmarkPagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    getBookmarksUseCase: GetBookmarkPagesUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {
    val flow = getBookmarksUseCase(PAGE_SIZE).cachedIn(viewModelScope)

    fun deleteFromBookmarks(bookmarkId: Int) = viewModelScope.launch {
        deleteBookmarkUseCase(bookmarkId)
    }
}
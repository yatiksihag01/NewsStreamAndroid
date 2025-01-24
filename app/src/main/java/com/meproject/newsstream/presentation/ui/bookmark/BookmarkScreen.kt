package com.meproject.newsstream.presentation.ui.bookmark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.components.ArticlesVerticalStaggeredGrid
import com.meproject.newsstream.presentation.ui.components.MainErrorMessageScreen
import com.meproject.newsstream.presentation.ui.components.ShimmerArticlesList
import com.meproject.newsstream.presentation.ui.components.launchCustomTab
import com.meproject.newsstream.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(modifier: Modifier = Modifier) {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val bookmarks = viewModel.flow.collectAsLazyPagingItems()
    val context = LocalContext.current
    val scrollState = rememberLazyStaggeredGridState()
    val toolbarColor = MaterialTheme.colorScheme.surfaceContainerHigh.toArgb()
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = bookmarks.loadState.refresh == LoadState.Loading,
        state = pullRefreshState,
        onRefresh = { bookmarks.refresh() },
        contentAlignment = Alignment.Center,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = bookmarks.loadState.refresh == LoadState.Loading,
                state = pullRefreshState,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    ) {
        when (bookmarks.loadState.refresh) {
            is LoadState.Loading -> {
                ShimmerArticlesList(
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }

            is LoadState.NotLoading -> {
                if (bookmarks.itemCount == 0) {
                    MainErrorMessageScreen(icon = Icons.Outlined.LocalLibrary,
                        messageId = R.string.you_haven_t_saved_any_article_yet,
                        onRetryClick = { bookmarks.refresh() })
                } else {
                    ArticlesVerticalStaggeredGrid(modifier = Modifier.fillMaxSize(),
                        gridState = scrollState,
                        articles = bookmarks,
                        onArticleClick = { url ->
                            launchCustomTab(
                                url = url, context = context, toolbarColor = toolbarColor
                            )
                        },
                        onBookmarkClick = { article ->
                            viewModel.toggleBookmark(article, article.isBookmarked)
                        })
                }
            }

            is LoadState.Error -> {
                MainErrorMessageScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.medium),
                    icon = Icons.Filled.CloudOff,
                    messageId = R.string.sorry_something_went_wrong,
                    onRetryClick = { bookmarks.retry() })
            }
        }
    }

}
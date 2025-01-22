package com.meproject.newsstream.presentation.ui.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.meproject.newsstream.R
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.presentation.ui.components.Article
import com.meproject.newsstream.presentation.ui.components.EndOfPageMessage
import com.meproject.newsstream.presentation.ui.components.MainErrorMessageScreen
import com.meproject.newsstream.presentation.ui.components.ShimmerArticlesList
import com.meproject.newsstream.presentation.ui.components.SummarySheet
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
    var shouldShowSummarySheet by rememberSaveable { mutableStateOf(false) }
    var title by rememberSaveable { mutableStateOf("") }

    if (shouldShowSummarySheet) {
        SummarySheet(
            onDismissRequest = {
                shouldShowSummarySheet = false
            },
            title = title,
            summaryResourceFlow = viewModel.summaryResourceFlow
        )
    }

    BookmarkScreen(
        pullToRefreshState = pullRefreshState,
        gridState = scrollState,
        bookmarks = bookmarks,
        onArticleClick = { url ->
            launchCustomTab(
                url = url,
                context = context,
                toolbarColor = toolbarColor
            )
        },
        isRefreshing = bookmarks.loadState.refresh == LoadState.Loading,
        onRefresh = { bookmarks.refresh() },
        onBookmarkClick = { article ->
            viewModel.toggleBookmark(article, article.isBookmarked)
            article.isBookmarked = !article.isBookmarked
        },
        onSummarizationClick = { article ->
            viewModel.getSummary(article.url)
            shouldShowSummarySheet = true
            title = article.title
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    gridState: LazyStaggeredGridState,
    bookmarks: LazyPagingItems<Article>,
    onArticleClick: (String) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onBookmarkClick: (Article) -> Unit,
    onSummarizationClick: (Article) -> Unit
) {
    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = { onRefresh() },
        contentAlignment = Alignment.Center,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
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
                    MainErrorMessageScreen(
                        icon = Icons.Outlined.LocalLibrary,
                        messageId = R.string.you_haven_t_saved_any_article_yet,
                        onRetryClick = { bookmarks.refresh() }
                    )
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(250.dp),
                        state = gridState,
                        modifier = modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(
                            MaterialTheme.spacing.extraSmall
                        ),
                    ) {
                        items(bookmarks.itemCount) { index ->
                            val article = bookmarks[index]
                            if (article != null) {
                                Article(
                                    title = article.title,
                                    thumbnailUrl = article.urlToImage,
                                    sourceName = article.source.name,
                                    publishedAt = article.publishedAt.substring(0..9),
                                    sentiment = article.sentiment,
                                    isBookmarked = article.isBookmarked,
                                    onArticleClick = { onArticleClick(article.url) },
                                    onBookmarkClick = {
                                        onBookmarkClick(article)
                                    },
                                    onSummarizationClick = { onSummarizationClick(article) }
                                )
                            }
                        }
                        if (bookmarks.loadState.append == LoadState.Loading) {
                            item { CircularProgressIndicator() }
                        }
                        if (bookmarks.loadState.append.endOfPaginationReached) {
                            item { EndOfPageMessage(Modifier.padding(MaterialTheme.spacing.large)) }
                        }
                    }
                }
            }

            is LoadState.Error -> {
                MainErrorMessageScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.medium),
                    icon = Icons.Filled.CloudOff,
                    messageId = R.string.sorry_something_went_wrong,
                    onRetryClick = { bookmarks.retry() }
                )
            }
        }
    }

}
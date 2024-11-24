package com.meproject.newsstream.presentation.ui.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.outlined.Dns
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.meproject.newsstream.R
import com.meproject.newsstream.domain.model.CategoryNewsItem
import com.meproject.newsstream.presentation.ui.components.Article
import com.meproject.newsstream.presentation.ui.components.EndOfPageMessage
import com.meproject.newsstream.presentation.ui.components.MainErrorMessageScreen
import com.meproject.newsstream.presentation.ui.components.ShimmerArticlesList
import com.meproject.newsstream.presentation.ui.components.SummarySheet
import com.meproject.newsstream.presentation.ui.components.launchCustomTab
import com.meproject.newsstream.presentation.ui.theme.spacing
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    val viewModel: ExploreViewModel = hiltViewModel()
    val allNewsPagingItems = viewModel.allNewsPageFlow.collectAsLazyPagingItems()
    val scrollState = rememberLazyStaggeredGridState()
    val pullRefreshState = rememberPullToRefreshState()
    val context = LocalContext.current
    val toolbarColor = MaterialTheme.colorScheme.surfaceContainerHigh.toArgb()
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

    ExploreScreen(
        pullToRefreshState = pullRefreshState,
        gridState = scrollState,
        allNewsPagingItems = allNewsPagingItems,
        onArticleClick = { url ->
            launchCustomTab(
                url = url,
                context = context,
                toolbarColor = toolbarColor
            )
        },
        isRefreshing = allNewsPagingItems.loadState.refresh == LoadState.Loading,
        onRefresh = { allNewsPagingItems.refresh() },
        onBookmarkClick = { article ->
            viewModel.toggleBookmark(article)
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
fun ExploreScreen(
    modifier: Modifier = Modifier,
    pullToRefreshState: PullToRefreshState,
    gridState: LazyStaggeredGridState,
    allNewsPagingItems: LazyPagingItems<CategoryNewsItem>,
    onArticleClick: (String) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onBookmarkClick: (CategoryNewsItem) -> Unit,
    onSummarizationClick: (CategoryNewsItem) -> Unit
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
        when (val refreshState = allNewsPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                ShimmerArticlesList(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    imageInFullWidth = false
                )
            }

            is LoadState.NotLoading -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(250.dp),
                    state = gridState,
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(
                        MaterialTheme.spacing.extraSmall
                    ),
                ) {
                    items(allNewsPagingItems.itemCount) { index ->
                        val article = allNewsPagingItems[index]
                        if (article != null) {
                            Article(
                                title = article.title,
                                thumbnailUrl = article.urlToImage,
                                sourceLogoUrl = article.sourceImageUrl,
                                sourceName = article.source,
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
                    if (allNewsPagingItems.loadState.append == LoadState.Loading) {
                        item { CircularProgressIndicator() }
                    }
                    if (allNewsPagingItems.loadState.append.endOfPaginationReached) {
                        item { EndOfPageMessage(Modifier.padding(MaterialTheme.spacing.large)) }
                    }
                }
            }

            is LoadState.Error -> {
                MainErrorMessageScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.medium),
                    icon = errorIcon(exception = refreshState.error.cause),
                    messageId = R.string.could_not_reach_server,
                    onRetryClick = { allNewsPagingItems.retry() }
                )
            }
        }
    }

}

@Composable
fun errorIcon(exception: Throwable?): ImageVector {
    return when (exception) {
        is IOException -> Icons.Filled.CloudOff
        else -> Icons.Outlined.Dns
    }
}
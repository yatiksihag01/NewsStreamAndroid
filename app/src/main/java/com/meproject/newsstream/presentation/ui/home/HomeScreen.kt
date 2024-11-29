package com.meproject.newsstream.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.meproject.newsstream.R
import com.meproject.newsstream.domain.model.Trending
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
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = hiltViewModel()
    val articles = viewModel.trendingPageFlow.collectAsLazyPagingItems()
    val scrollState = rememberLazyStaggeredGridState()
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

    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val windowHeightSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowHeightSizeClass
    val noOfColumns =
        if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) 1
        else if (windowWidthSizeClass == WindowWidthSizeClass.MEDIUM
            && windowHeightSizeClass == WindowHeightSizeClass.COMPACT
        ) 2
        else 3

    val context = LocalContext.current
    val toolbarColor = MaterialTheme.colorScheme.surfaceContainerHigh.toArgb()

    when (val refreshState = articles.loadState.refresh) {
        is LoadState.Loading -> {
            ShimmerArticlesList(
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        is LoadState.Error -> {
            val icon = when (refreshState.error.cause) {
                is IOException -> Icons.Filled.CloudOff
                else -> Icons.Outlined.Dns
            }
            val messageId = when (refreshState.error.cause) {
                is IOException -> R.string.no_internet_connection
                else -> R.string.could_not_reach_server
            }
            MainErrorMessageScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium),
                icon = icon,
                messageId = messageId,
                onRetryClick = { articles.retry() }
            )

        }

        is LoadState.NotLoading -> {
            HomeScreen(
                modifier = Modifier,
                articles = articles,
                gridState = scrollState,
                noOfColumns = noOfColumns,
                onBookmarkClick = { article -> viewModel.toggleBookmark(article) },
                onSummarizationClick = { article ->
                    viewModel.getSummary(article.url)
                    shouldShowSummarySheet = true
                    title = article.title
                }
            ) { url ->
                launchCustomTab(
                    url = url,
                    context = context,
                    toolbarColor = toolbarColor
                )
            }
        }
    }

}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Trending>,
    gridState: LazyStaggeredGridState,
    noOfColumns: Int = 1,
    onBookmarkClick: (Trending) -> Unit,
    onSummarizationClick: (Trending) -> Unit,
    onArticleClick: (String) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(noOfColumns),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        verticalItemSpacing = MaterialTheme.spacing.extraSmall,
    ) {
        items(count = articles.itemCount) { index ->
            val article = articles[index]
            if (article != null) {
                Article(
                    title = article.title,
                    thumbnailUrl = article.urlToImage ?: "",
                    sourceName = article.source,
                    publishedAt = article.publishedAt.substring(0..9),
                    sentiment = article.sentiment,
                    isBookmarked = article.isBookmarked,
                    onArticleClick = { onArticleClick(article.url) },
                    onBookmarkClick = {
                        onBookmarkClick(article)
                        article.isBookmarked = !article.isBookmarked
                    },
                    onSummarizationClick = { onSummarizationClick(article) }
                )
            }
        }
        if (articles.loadState.append == LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(MaterialTheme.spacing.large),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                    )
                }
            }
        }
        if (articles.loadState.append.endOfPaginationReached) {
            item { EndOfPageMessage(Modifier.padding(MaterialTheme.spacing.large)) }
        }
    }

}
package com.meproject.newsstream.presentation.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.outlined.Dns
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
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel: HomeViewModel = hiltViewModel()
    val articles = viewModel.trendingPageFlow.collectAsLazyPagingItems()
    val scrollState = rememberLazyStaggeredGridState()
    val context = LocalContext.current
    val toolbarColor = MaterialTheme.colorScheme.surfaceContainerHigh.toArgb()
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = articles.loadState.refresh == LoadState.Loading,
        state = pullRefreshState,
        onRefresh = { articles.refresh() },
        contentAlignment = Alignment.Center,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = articles.loadState.refresh == LoadState.Loading,
                state = pullRefreshState,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    ) {

        when (val refreshState = articles.loadState.refresh) {
            is LoadState.Loading -> {
                ShimmerArticlesList(
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }

            is LoadState.NotLoading -> {
                ArticlesVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxWidth(),
                    gridState = scrollState,
                    articles = articles,
                    onArticleClick = { url ->
                        launchCustomTab(
                            url = url,
                            context = context,
                            toolbarColor = toolbarColor
                        )
                    },
                    onBookmarkClick = { article -> viewModel.toggleBookmark(article) }
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
        }
    }


}
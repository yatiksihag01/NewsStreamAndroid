package com.meproject.newsstream.presentation.ui.bookmark

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.core.layout.WindowWidthSizeClass
import com.meproject.newsstream.R
import com.meproject.newsstream.domain.model.Bookmark
import com.meproject.newsstream.presentation.ui.components.Article
import com.meproject.newsstream.presentation.ui.components.launchCustomTab
import com.meproject.newsstream.presentation.ui.theme.NewsStreamTheme
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun BookmarkScreen() {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val bookmarks = viewModel.flow.collectAsLazyPagingItems()
    StatelessBookmarkScreen(
        bookmarks = bookmarks,
    ) {

    }

}

@Composable
private fun StatelessBookmarkScreen(
    modifier: Modifier = Modifier,
    bookmarks: LazyPagingItems<Bookmark>,
    uiEvent: (BookmarkUiEvent) -> Unit,
) {
    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val noOfColumns = if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) 1 else 2
    Box (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        if (bookmarks.loadState.refresh == LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (bookmarks.itemCount == 0) {
            BookmarkMessageScreen(
                messageId = R.string.you_haven_t_saved_any_article_yet,
                icon = Icons.Outlined.LocalLibrary
            )
        } else {
            LazyVerticalGrid (
                columns = GridCells.Fixed(noOfColumns),
                modifier = modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            ) {
                items(bookmarks.itemCount) { index ->
                    val article = bookmarks[index]
                    if (article != null) {
                        Article(
                            title = article.title,
                            thumbnailUrl = article.urlToImage,
                            sourceName = article.source,
                            publishedAt = article.publishedAt,
                            sentiment = article.sentiment,
                            onArticleClick = {

                            },
                            onBookmarkClick = { },
                            onSummarizationClick = {}
                        )
                    }
                }
                if (bookmarks.loadState.append == LoadState.Loading) {
                    item { CircularProgressIndicator() }
                }
            }
        }
    }

}

@Composable
private fun CustomTab(url: String) {
    val context = LocalContext.current
    val toolbarColor = MaterialTheme.colorScheme.surfaceContainerHigh.toArgb()
    launchCustomTab(url, context, toolbarColor)
}

@Composable
private fun BookmarkMessageScreen(
    modifier: Modifier = Modifier,
    @StringRes messageId: Int,
    icon: ImageVector,
) {
    Box (
        modifier = modifier
    ) {
        Column (
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
            Text(
                text = stringResource(id = messageId),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
private fun BookmarkMessageScreenPreview() {
    NewsStreamTheme {
        Surface {
            BookmarkMessageScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                R.string.you_haven_t_saved_any_article_yet,
                Icons.Outlined.LocalLibrary
            )

        }
    }
}
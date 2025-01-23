package com.meproject.newsstream.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.meproject.newsstream.domain.model.Article
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun ArticlesVerticalStaggeredGrid(
    modifier: Modifier = Modifier,
    gridState: LazyStaggeredGridState,
    articles: LazyPagingItems<Article>,
    onArticleClick: (String) -> Unit,
    onBookmarkClick: (Article) -> Unit
) {

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(200.dp),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
    ) {
        items(count = articles.itemCount) { index ->
            val article = articles[index]
            if (article != null) {
                ArticleCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.medium,
                            vertical = MaterialTheme.spacing.extraSmall
                        ),
                    urlToImage = article.urlToImage,
                    source = article.source,
                    publishedAt = article.publishedAt,
                    isBookmarked = article.isBookmarked,
                    title = article.title,
                    description = article.description,
                    onBookmarkClick = { onBookmarkClick(article) }
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
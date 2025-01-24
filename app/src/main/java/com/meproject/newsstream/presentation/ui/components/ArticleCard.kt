package com.meproject.newsstream.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.meproject.newsstream.R
import com.meproject.newsstream.domain.model.Source
import com.meproject.newsstream.presentation.ui.theme.newsStreamTypography
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    urlToImage: String?,
    source: Source,
    publishedAt: String,
    isBookmarked: Boolean,
    title: String,
    description: String?,
    onBookmarkClick: () -> Unit
) {
    Column (modifier = modifier) {
        AsyncImage(
            model = urlToImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = shapes.extraSmall),
            placeholder = painterResource(R.drawable.article_image_placeholder),
            contentScale = ContentScale.FillWidth
        )
        MetadataRow(
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.small),
            sourceName = source.name,
            sourceImageUrl = source.imageUrl,
            publishedAt = publishedAt,
            isBookmarked = isBookmarked,
            onBookmarkClick = { onBookmarkClick() }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            modifier = Modifier
                .paddingFromBaseline(
                    top = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.small
                ),
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        if (!description.isNullOrEmpty()) {
            ResizableTextBox(
                modifier =  Modifier
                    .fillMaxWidth()
                    .clip(shape = shapes.extraSmall)
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHigh),
                text = description
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))
    }
}

@Composable
private fun MetadataRow(
    modifier: Modifier = Modifier,
    sourceName: String,
    sourceImageUrl: String?,
    publishedAt: String,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            if (!sourceImageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = sourceImageUrl,
                    contentDescription = sourceName,
                    modifier = Modifier
                        .size(22.dp)
                        .clip(shape = shapes.extraSmall)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            }
            Text(
                modifier = Modifier
                    .alpha(0.8f)
                    .align(Alignment.CenterVertically),
                text = "$sourceName · ${publishedAt.substring(0..9)}",
                style = newsStreamTypography.labelLarge,
                maxLines = 1,
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(
            modifier = Modifier.size(22.dp),
            onClick = { onBookmarkClick() }
        ) {
            Icon(
                imageVector = if (isBookmarked) {
                    Icons.Filled.Bookmark
                } else Icons.Outlined.BookmarkBorder,
                contentDescription = stringResource(R.string.bookmark)
            )
        }
    }
}
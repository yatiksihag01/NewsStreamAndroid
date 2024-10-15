package com.meproject.newsstream.presentation.ui.components

import android.content.res.Configuration
import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentNeutral
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.theme.NewsStreamTheme
import com.meproject.newsstream.presentation.ui.theme.newsStreamTypography
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun Article(
    modifier: Modifier = Modifier,
    title: String,
    thumbnailUrl: String,
    sourceLogoUrl: String,
    sourceName: String,
    publishedAt: String,
    sentiment: String,
    onArticleClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onSummarizationClick: () -> Unit,
) {
    Column (
        modifier = modifier
            .clickable { onArticleClick() }
            .clip(shape = shapes.small)
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        BottomRow(
            modifier = Modifier,
            sourceLogoUrl = sourceLogoUrl,
            sourceName = sourceName,
            publishedAt = publishedAt,
            sentiment = sentiment,
            onBookmarkClick = { onBookmarkClick() },
            onSummarizationClick = { onSummarizationClick() }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        HorizontalDivider()
    }
}

@Composable
private fun BottomRow(
    modifier: Modifier = Modifier,
    sourceLogoUrl: String,
    sourceName: String,
    publishedAt: String,
    sentiment: String,
    isBookmarked: Boolean = false,
    onBookmarkClick: () -> Unit,
    onSummarizationClick: () -> Unit,
) {
    Row (
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = sourceLogoUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clip(shape = shapes.extraSmall),
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            Text(
                text = sourceName.separateWithCentreDot(publishedAt),
                style = newsStreamTypography.labelLarge
            )
        }
        Row (
            modifier = Modifier.padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { onSummarizationClick() }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.8f),
                    painter = painterResource(id = R.drawable.summarize),
                    contentDescription = stringResource(id = R.string.summarize)
                )
            }
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.8f),
                    imageVector = sentiment.toSentimentIcon(),
                    contentDescription = sentiment,
                    tint = sentiment.toSentimentIcon().toSentimentIconColor()
                )
            }
            val bookmarkIcon = if (isBookmarked) {
                Icons.Filled.Bookmark
            } else {
                Icons.Outlined.BookmarkBorder
            }
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { onBookmarkClick() }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.8f),
                    imageVector = bookmarkIcon,
                    contentDescription = stringResource(R.string.bookmark)
                )
            }
        }
    }
}

private fun String.separateWithCentreDot(suffix: String): AnnotatedString {
    return buildAnnotatedString {
        append(this@separateWithCentreDot)
        append("  •  ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(suffix)
        }
    }
}

private fun String.toSentimentIcon(): ImageVector {
    return when(this) {
        "Positive" -> Icons.Outlined.SentimentSatisfied
        "Negative" -> Icons.Outlined.SentimentDissatisfied
        "Neutral" -> Icons.Outlined.SentimentNeutral
        else -> Icons.Filled.QuestionMark
    }
}

@Composable
private fun ImageVector.toSentimentIconColor(): Color {
    return when(this) {
        Icons.Outlined.SentimentSatisfied -> Color.Green
        Icons.Outlined.SentimentDissatisfied -> Color.Red
        Icons.Outlined.SentimentNeutral -> Color.Yellow
        else -> Color.Unspecified
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun BottomRowPreview() {
    NewsStreamTheme {
        Surface {
            BottomRow(
                sourceLogoUrl = "",
                sourceName = "News Stream",
                publishedAt = "15-10-2024",
                sentiment = "",
                onBookmarkClick = {},
                onSummarizationClick = {}
            )
        }
    }
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun ArticlePreview() {
    NewsStreamTheme {
        Surface {
            Article(
                title = "India is Russia's no 2 supplier of restricted tech, say US, EU officials",
                thumbnailUrl = "",
                sourceLogoUrl = "",
                sourceName = "News Stream",
                publishedAt = "15-10-2024",
                sentiment = "Positive",
                onArticleClick = {},
                onBookmarkClick = {},
                onSummarizationClick = {}
            )
        }
    }
}
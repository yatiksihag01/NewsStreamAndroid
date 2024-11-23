package com.meproject.newsstream.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentNeutral
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.theme.NewsStreamTheme
import com.meproject.newsstream.presentation.ui.theme.newsStreamTypography
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun Article(
    modifier: Modifier = Modifier,
    title: String,
    thumbnailUrl: String?,
    sourceLogoUrl: String? = null,
    sourceName: String,
    publishedAt: String,
    sentiment: String,
    isBookmarked: Boolean = false,
    onArticleClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onSummarizationClick: () -> Unit,
) {
    var bookmarkIconState by rememberSaveable { mutableStateOf(isBookmarked) }
    Column (modifier = modifier) {
        Column (
            modifier = Modifier.clickable { onArticleClick() }
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            ImageAndTitle(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium),
                imageUrl = thumbnailUrl,
                title = title
            )
            BottomRow(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.small
                    ),
                sourceLogoUrl = sourceLogoUrl,
                sourceName = sourceName,
                publishedAt = publishedAt,
                sentiment = sentiment,
                isBookmarked = bookmarkIconState,
                onBookmarkClick = {
                    onBookmarkClick()
                    bookmarkIconState = !bookmarkIconState
                },
                onSummarizationClick = { onSummarizationClick() }
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .clip(shape = shapes.small),
            thickness = 4.dp
        )
    }
}

@Composable
private fun ImageAndTitle(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String
) {
    var isImageSmall by remember { mutableStateOf(false) }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    if (painter.state is AsyncImagePainter.State.Success) {
        isImageSmall = painter.intrinsicSize.width < 150.dp.value
    }
    if (isImageSmall) {
        ImageTitleInRow(
            modifier = modifier,
            painter = painter,
            title = title
        )
    } else {
        ImageTitleInColumn(
            modifier = modifier,
            painter = painter,
            title = title
        )
    }
}

@Composable
private fun ImageTitleInRow(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter,
    title: String
) {
    Row (modifier = modifier) {
        Image(
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height)
                .clip(shape = shapes.small),
            painter = painter,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            modifier = Modifier.weight(2.0f),
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ImageTitleInColumn(
    modifier: Modifier = Modifier,
    painter: AsyncImagePainter,
    title: String
) {
    Column (modifier = modifier) {
        Image(
            painter = if (painter.state is AsyncImagePainter.State.Success)
                painter else painterResource(id = R.drawable.article_image_placeholder),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = shapes.small),
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .paddingFromBaseline(top = MaterialTheme.spacing.extraLarge),
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun BottomRow(
    modifier: Modifier = Modifier,
    sourceLogoUrl: String? = null,
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
            modifier = Modifier.weight(2.0f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!sourceLogoUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = sourceLogoUrl,
                    contentDescription = sourceName,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(shape = shapes.extraSmall),
                    placeholder = painterResource(id = R.drawable.article_image_placeholder)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            }
            Text(
                modifier = Modifier.alpha(0.8f),
                text = sourceName.separateWithCentreDot(publishedAt),
                style = newsStreamTypography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                    modifier = Modifier.fillMaxSize(0.7f),
                    painter = painterResource(id = R.drawable.summarize),
                    contentDescription = stringResource(id = R.string.summarize)
                )
            }
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.7f),
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
                    modifier = Modifier.fillMaxSize(0.7f),
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
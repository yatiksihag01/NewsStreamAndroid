package com.meproject.newsstream.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.meproject.newsstream.presentation.ui.components.Article
import com.meproject.newsstream.presentation.ui.components.launchCustomTab
import com.meproject.newsstream.presentation.ui.theme.spacing

class Articles(
    val title: String,
    val thumbnailUrl: String,
    val sourceLogoUrl: String,
    val sourceName: String,
    val publishedAt: String,
    val sentiment: String,
)

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val noOfColumns = if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) 1 else 2
    val title = "A critical flaw in Kubernetes Image Builder could allow attackers to gain root access"
    val url = "https://securityaffairs.com/wp-content/uploads/2020/07/Kubernetes.jpg"

    val context = LocalContext.current
    val toolbarColor = MaterialTheme.colorScheme.surfaceContainerHigh.toArgb()

    val a = Articles(
        title = title,
        thumbnailUrl = url,
        sourceLogoUrl = "",
        sourceName = "Securityaffairs.com",
        publishedAt = "2024-10-17",
        sentiment = "Negative")

    val b = Articles(
        title = "Stellantis Must Honor Investment Commitments: White House",
        thumbnailUrl = "https://i0.wp.com/electrek.co/wp-content/uploads/sites/3/2022/07/Omead-Afshar-with-Elon-Musk-at-Tesla-Cyber-Rodeo.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1",
        sourceLogoUrl = "",
        sourceName = "International Business Times",
        publishedAt = "2024-10-17",
        sentiment = "Positive")

    val c = Articles(
        title = "Climate Activists Push for Carbon Tax With Measure GG, But Critics Warn it Could Backfire",
        thumbnailUrl = "https://cdn.kqed.org/wp-content/uploads/sites/35/2024/10/GettyImages-1347890261-1020x680.jpg",
        sourceLogoUrl = "",
        sourceName = "Fortune",
        publishedAt = "2024-10-17",
        sentiment = "Negative")

    val articles = listOf(a,b,c,b,c,a,a,c)

    LazyVerticalGrid (
        columns = GridCells.Fixed(noOfColumns),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
    ) {
        items(articles) { article ->
            Article(
                title = article.title,
                thumbnailUrl = article.thumbnailUrl,
                sourceLogoUrl = article.sourceLogoUrl,
                sourceName = article.sourceName,
                publishedAt = article.publishedAt,
                sentiment = article.sentiment,
                onArticleClick = { launchCustomTab(
                    "https://securityaffairs.com/169919/security/kubernetes-image-builder-critical-flaw.html",
                    context = context,
                    toolbarColor = toolbarColor
                ) },
                onBookmarkClick = { /*TODO*/ }) {
            }
        }
    }
}
package com.meproject.newsstream.presentation.ui.explore

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meproject.newsstream.presentation.ui.components.ShimmerArticlesList

@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    ShimmerArticlesList(
        modifier = Modifier.padding(horizontal = 16.dp),
        imageInFullWidth = false
    )

}
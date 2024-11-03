package com.meproject.newsstream.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun ShimmerArticlesList(
    modifier: Modifier = Modifier, imageInFullWidth: Boolean = true
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(250.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
    ) {
        items(count = 10) {
            if (imageInFullWidth) {
                ArticleWithFullWidthImg()
            } else {
                ArticleWithSmallWidthImg(modifier = Modifier)
            }
        }
    }
}

@Composable
private fun ArticleWithFullWidthImg(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(shape = shapes.small)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        TitleShimmer()
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        BottomRowShimmer()
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        HorizontalDivider(
            modifier = Modifier.clip(shape = shapes.small), thickness = 4.dp
        )

    }
}

@Composable
private fun ArticleWithSmallWidthImg(modifier: Modifier = Modifier) {
    Column {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Row {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            TitleShimmer()
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        BottomRowShimmer()
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        HorizontalDivider(
            modifier = Modifier.clip(shape = shapes.small), thickness = 4.dp
        )
    }
}

@Composable
private fun TitleShimmer() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(shape = shapes.small)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(shape = shapes.small)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .clip(shape = shapes.small)
                .align(Alignment.Start)
                .shimmerEffect()
        )
    }
}

@Composable
private fun BottomRowShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(24.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
        }
    }
}
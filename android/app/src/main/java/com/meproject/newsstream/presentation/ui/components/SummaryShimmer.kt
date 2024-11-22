package com.meproject.newsstream.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun SummaryShimmer(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(count = 15) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(8.dp)
                    .clip(shape = shapes.small)
                    .shimmerEffect()
            )
        }
    }

}
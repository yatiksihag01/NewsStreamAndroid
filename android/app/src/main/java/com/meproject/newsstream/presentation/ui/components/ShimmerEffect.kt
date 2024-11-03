package com.meproject.newsstream.presentation.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "ShimmerInfiniteTransition")
    val startOffset by transition.animateFloat(
        initialValue = -1.5f * size.width.toFloat(),
        targetValue = 1.5f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1500)
        ),
        label = "ShimmerStartOffset"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xDA616161),
                Color(0x80616161),
                Color(0xDA616161),
            ),
            start = Offset(startOffset, 0f),
            end = Offset(startOffset + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
            size = it.size
        }
}
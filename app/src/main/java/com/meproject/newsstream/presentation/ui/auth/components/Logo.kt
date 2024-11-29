package com.meproject.newsstream.presentation.ui.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .clip(shape = shapes.small)
        .background(MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_foreground),
            contentDescription = "",
            modifier = Modifier.padding(MaterialTheme.spacing.large)
        )
    }
}
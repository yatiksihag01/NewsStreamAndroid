package com.meproject.newsstream.presentation.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.theme.spacing

/**
 * A composable that displays an icon, error message and an optional retry button in a column centered.
 */
@Composable
fun MainErrorMessageScreen(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes messageId: Int,
    shouldShowRetryButton: Boolean = true,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
        Text(
            text = stringResource(id = messageId),
            style = MaterialTheme.typography.titleMedium
        )
        if (shouldShowRetryButton) {
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
            Button(onClick = onRetryClick) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

/**
 * A composable that displays a tick icon and message in a row centered
 * that the end of the page has been reached.
 */
@Composable
fun EndOfPageMessage(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Verified,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(R.string.end_of_the_page_msg)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = stringResource(R.string.end_of_the_page_msg),
            style = MaterialTheme.typography.labelLarge
        )
    }
}
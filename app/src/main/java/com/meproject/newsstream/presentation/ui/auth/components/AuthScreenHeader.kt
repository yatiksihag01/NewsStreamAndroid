package com.meproject.newsstream.presentation.ui.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.theme.newsStreamTypography
import com.meproject.newsstream.presentation.ui.theme.spacing

/**
 * Composable function that displays the header for authentication screens.
 *
 * This header includes the app logo, welcome message, and tagline.
 *
 * @param modifier Modifier used to adjust the layout or styling of the header.
 */
@Composable
fun AuthScreenHeader(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo(Modifier.padding(top = MaterialTheme.spacing.large))
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        Text(
            text = stringResource(R.string.welcome),
            style = newsStreamTypography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
        Text(
            text = stringResource(R.string.tagline),
            style = newsStreamTypography.titleSmall,
            modifier = Modifier.alpha(0.75f)
        )
    }
}
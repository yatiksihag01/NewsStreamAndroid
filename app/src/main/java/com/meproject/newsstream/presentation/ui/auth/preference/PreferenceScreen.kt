package com.meproject.newsstream.presentation.ui.auth.preference

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.auth.components.Logo
import com.meproject.newsstream.presentation.ui.theme.NewsStreamTheme
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun PreferenceScreen(
    modifier: Modifier = Modifier,
    uiState: PreferenceUiState,
    uiEvent: (PreferenceUiEvent) -> Unit,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    onNavigationToHome: () -> Unit
) {
    val categories = uiState.categories
    val maxItemsInEachRow =
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> 3
            WindowWidthSizeClass.MEDIUM -> 4
            else -> 5
        }

    Column (
        modifier = modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Column {
            Text(
                text = stringResource(id = R.string.preference_screen_headline),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.alpha(0.9f)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            if (uiState.isFetching) CircularProgressIndicator()
            FlowRow (
                maxItemsInEachRow =  maxItemsInEachRow,
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Center
            ) {
                categories.forEach { category ->
                    FilterChip(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                        onClick = { uiEvent(PreferenceUiEvent.CategorySelected(category)) },
                        label = {
                            Text(category.name)
                        },
                        selected = category.isSelected,
                    )
                }
            }
        }
        Button(
            onClick = { uiEvent(PreferenceUiEvent.SavePreferences) },
            shape = shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.continuee))
        }
        uiState.errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
        if (uiState.isSaving) {
            CircularProgressIndicator(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
            )
        }
        if (uiState.navigateToHome) {
            onNavigationToHome()
            uiEvent(PreferenceUiEvent.PreferencesSaved)
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
private fun PreferenceScreenPreview() {
    NewsStreamTheme {
        Surface {
            PreferenceScreen(
                uiState = PreferenceUiState(),
                uiEvent = {},
                onNavigationToHome = {}
            )
        }
    }
}
package com.meproject.newsstream.presentation.ui.auth.login

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.meproject.newsstream.R
import com.meproject.newsstream.presentation.ui.auth.components.AuthOutlinedTextField
import com.meproject.newsstream.presentation.ui.auth.components.AuthScreenHeader
import com.meproject.newsstream.presentation.ui.auth.components.SignUpAnnotatedString
import com.meproject.newsstream.presentation.ui.theme.NewsStreamTheme
import com.meproject.newsstream.presentation.ui.theme.shapes
import com.meproject.newsstream.presentation.ui.theme.spacing
import com.meproject.newsstream.presentation.ui.utils.rememberImeState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    uiEvent: (LoginUiEvent) -> Unit,
    onNavigationToHome: () -> Unit,
    onNavigationToSignup: () -> Unit,
    onNavigationToForgotPassword: () -> Unit,
) {
    val isKeyboardVisible by rememberImeState()
    val scrollState = rememberScrollState()

    val layoutDirection = LocalLayoutDirection.current
    val displayCutout = WindowInsets.displayCutout.asPaddingValues()
    val startPadding = displayCutout.calculateStartPadding(layoutDirection)
    val endPadding = displayCutout.calculateEndPadding(layoutDirection)

    LaunchedEffect(key1 = isKeyboardVisible) {
        if (isKeyboardVisible) scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthScreenHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.extraLarge)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = startPadding.coerceAtLeast(MaterialTheme.spacing.extraLarge),
                        end = endPadding.coerceAtLeast(MaterialTheme.spacing.extraLarge)
                    )
                )
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthOutlinedTextField(
                value = uiState.email,
                onValueChange = { uiEvent(LoginUiEvent.EmailChanged(it.trim())) },
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.email)
            )
            AuthOutlinedTextField(
                value = uiState.password,
                onValueChange = { uiEvent(LoginUiEvent.PasswordChanged(it.trim())) },
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.password),
                isPassword = true,
            )
            Button(
                onClick = { uiEvent(LoginUiEvent.LoginClick) },
                shape = shapes.small,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isLoginEnabled && !uiState.isLoading
            ) {
                Text(text = stringResource(R.string.login))
            }
            uiState.errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
            TextButton(
                onClick = { onNavigationToForgotPassword() }
            ) {
                Text(stringResource(R.string.forgot_password))
            }
            if (uiState.isLoading) {
                CircularProgressIndicator()
            }
        }
        SignUpAnnotatedString(
            Modifier.padding(0.dp),
            onSuffixClick = { onNavigationToSignup() },
            stringResource(id = R.string.don_t_have_an_account),
            stringResource(id = R.string.sign_up)
        )

        if (uiState.navigateToHome) {
            onNavigationToHome()
            uiEvent(LoginUiEvent.LoggedIn)
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
fun LoginScreenPreview() {
    NewsStreamTheme {
        Surface {
            LoginScreen(
                uiState = LoginUiState(
                    email = "email@email.com",
                    password = "password",
                    isLoginEnabled = true,
                    isLoading = false,
                    errorMessage = null
                ),
                uiEvent = {},
                onNavigationToHome = {},
                onNavigationToSignup = {},
                onNavigationToForgotPassword = {}
            )
        }
    }
}
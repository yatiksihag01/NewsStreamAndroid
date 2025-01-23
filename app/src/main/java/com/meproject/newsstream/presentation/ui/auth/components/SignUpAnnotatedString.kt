package com.meproject.newsstream.presentation.ui.auth.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

/**
 * Displays a text with a clickable suffix.
 *
 * This composable function creates an annotated string with a prefix and a clickable suffix.
 * The prefix is displayed in the default text color, while the suffix is displayed in the primary color
 * and is clickable. When the suffix is clicked, the provided `onSuffixClick` lambda is invoked.
 *
 * @param modifier The modifier to be applied to the composable.
 * @param onSuffixClick The lambda to be invoked when the sign-up suffix is clicked.
 * @param prefix The text to be displayed before the clickable suffix.
 * @param clickableSuffix The clickable text that triggers the sign-up action.
 */
@Composable
fun SignUpAnnotatedString(
    modifier: Modifier = Modifier,
    onSuffixClick: () -> Unit,
    prefix: String,
    clickableSuffix: String
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append("$prefix ")
        }
        pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(clickableSuffix)
        }
        pop() // Remove annotation after clickable part
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                .firstOrNull()?.let {
                    onSuffixClick()
                }
        }
    )
}
package com.meproject.newsstream.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.meproject.newsstream.presentation.ui.theme.newsStreamTypography

/**
 * A composable that displays a text box that can be expanded to show more content.
 *
 * This component displays a given text within a [Text] composable.
 * Initially, it shows only one line of text with an ellipsis at the end if the text is longer.
 * When the user clicks on the text, it expands to show all of the content.
 * Clicking again collapses it back to a single line.
 *
 * @param modifier The modifier to be applied to the Column containing the text.
 * @param text The text to be displayed within the text box.
 */
@Composable
fun ResizableTextBox(
    modifier: Modifier,
    text: String,
) {
    var expanded by remember { mutableStateOf(false) }
    Column (
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
            .clickable { expanded = !expanded }
    ) {
        Text(
            modifier = Modifier.alpha(0.8f).padding(horizontal = 2.dp),
            text = text,
            style = newsStreamTypography.bodyLarge,
            maxLines = if (expanded) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
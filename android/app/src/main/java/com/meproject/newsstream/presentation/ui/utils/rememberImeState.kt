package com.meproject.newsstream.presentation.ui.utils

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * A composable function that tracks the visibility state of the software keyboard (IME).
 *
 * This function uses a [ViewTreeObserver.OnGlobalLayoutListener] to detect changes in the
 * layout of the current view. It uses [ViewCompat.getRootWindowInsets] method to fetch
 * original [WindowInsetsCompat] that are dispatched to the view hierarchy and then checks if the
 * software keyboard is open or closed. The result is stored in a remembered state that can be
 * observed by other composable.
 *
 * @return [State]<Boolean> - A state representing whether the keyboard is visible (`true`)
 * or not (`false`).
 *
 * ### Example
 * ```
 * @Composable
 * fun KeyboardAwareScreen() {
 *     val isKeyboardVisible by rememberImeState()
 *
 *     if (isKeyboardVisible) {
 *         // Perform actions when keyboard is visible
 *     } else {
 *         // Perform actions when keyboard is hidden
 *     }
 * }
 * ```
 *
 * @see ViewTreeObserver.OnGlobalLayoutListener
 * @see DisposableEffect
 */
@Composable
fun rememberImeState(): State<Boolean> {
    val state = remember { mutableStateOf(false) }
    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat
                .getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            state.value = isKeyboardOpen
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return state
}
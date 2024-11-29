package com.meproject.newsstream.presentation.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

fun launchCustomTab(
    url: String,
    context: Context,
    toolbarColor: Int,
) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShareState(CustomTabsIntent.SHARE_STATE_ON)
        .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
            .setToolbarColor(toolbarColor)
            .build())
        .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, CustomTabColorSchemeParams.Builder()
            .setToolbarColor(toolbarColor)
            .build())
        .setUrlBarHidingEnabled(true)
        .setShowTitle(true)
        .build()
    customTabsIntent.intent.putExtra(
        Intent.EXTRA_REFERRER,
        Uri.parse("android-app://" + context.packageName)
    )
    try {
        customTabsIntent.intent.setPackage("com.android.chrome")
        customTabsIntent.launchUrl(context, Uri.parse(url))
    } catch (e: Exception) {
        customTabsIntent.launchUrl(context, Uri.parse(url))
        e.printStackTrace()
    }
}

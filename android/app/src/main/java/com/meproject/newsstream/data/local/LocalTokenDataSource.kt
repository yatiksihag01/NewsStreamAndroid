package com.meproject.newsstream.data.local

import android.content.Context
import com.meproject.newsstream.common.Constants.PREFS_TOKEN_FILE
import com.meproject.newsstream.common.Constants.PREFS_ACCESS_TOKEN_KEY
import com.meproject.newsstream.common.Constants.PREFS_REFRESH_TOKEN_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalTokenDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(PREFS_ACCESS_TOKEN_KEY, token).apply()
    }

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString(PREFS_REFRESH_TOKEN_KEY, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(PREFS_ACCESS_TOKEN_KEY, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(PREFS_REFRESH_TOKEN_KEY, null)
    }

    fun clearTokens() {
        sharedPreferences.edit().remove(PREFS_ACCESS_TOKEN_KEY).apply()
        sharedPreferences.edit().remove(PREFS_REFRESH_TOKEN_KEY).apply()
    }
}
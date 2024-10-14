package com.meproject.newsstream.data.local

import android.content.Context
import com.meproject.newsstream.common.Constants.PREFS_TOKEN_FILE
import com.meproject.newsstream.common.Constants.PREFS_TOKEN_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalTokenDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(PREFS_TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(PREFS_TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(PREFS_TOKEN_KEY).apply()
    }
}
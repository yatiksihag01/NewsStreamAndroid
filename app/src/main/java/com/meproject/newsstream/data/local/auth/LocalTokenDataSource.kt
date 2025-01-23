package com.meproject.newsstream.data.local.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.meproject.newsstream.common.Constants.PREFS_ACCESS_TOKEN_KEY
import com.meproject.newsstream.common.Constants.PREFS_REFRESH_TOKEN_KEY
import com.meproject.newsstream.common.Constants.PREFS_TOKEN_FILE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * A class responsible for managing the storage and retrieval of access and refresh tokens
 * from a shared preferences file.
 */
class LocalTokenDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    private val _tokenFlow = MutableStateFlow(
        sharedPreferences.getString(PREFS_ACCESS_TOKEN_KEY, null)
    )
    val tokenFlow: StateFlow<String?> = _tokenFlow

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == PREFS_ACCESS_TOKEN_KEY) {
                _tokenFlow.value = sharedPreferences.getString(PREFS_ACCESS_TOKEN_KEY, null)
            }
        }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
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
        sharedPreferences.edit {
            remove(PREFS_ACCESS_TOKEN_KEY)
            remove(PREFS_REFRESH_TOKEN_KEY)
            apply()
        }
    }

}
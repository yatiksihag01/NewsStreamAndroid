package com.meproject.newsstream.data.remote.dto.auth.login

import com.google.gson.annotations.SerializedName

data class RefreshRequest(
    @SerializedName("refresh_token")
    val refreshToken: String
)

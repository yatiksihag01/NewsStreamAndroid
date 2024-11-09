package com.meproject.newsstream.data.remote

import android.util.Log
import com.meproject.newsstream.data.remote.dto.auth.login.LoginDto
import com.meproject.newsstream.data.remote.dto.auth.login.LoginResponse
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RemoteTokenDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    suspend fun fetchToken(email: String, password: String): String {
        return try {
            val token = authApi.login(loginDto = LoginDto(email, password)).accessToken
            Log.d("TOKEN", "Token fetched: $token")
            token
        } catch (e: HttpException) {
            throw e
        }
    }
    fun refreshTokens(refreshToken: String): Response<LoginResponse> =
        authApi.refreshToken(refreshToken = refreshToken)
}
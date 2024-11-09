package com.meproject.newsstream.data.remote

import com.meproject.newsstream.data.remote.dto.auth.login.LoginDto
import com.meproject.newsstream.data.remote.dto.auth.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("/login/")
    suspend fun login(
        @Header("ngrok-skip-browser-warning") value: String = "5415",
        @Body loginDto: LoginDto
    ): LoginResponse

    @POST("/refresh/")
    fun refreshToken(
        @Header("ngrok-skip-browser-warning") value: String = "5415",
        @Body refreshToken: String
    ): Response<LoginResponse>
}
package com.meproject.newsstream.data.remote.api

import com.meproject.newsstream.data.remote.dto.auth.login.LoginDetailsDto
import com.meproject.newsstream.data.remote.dto.auth.login.LoginResponse
import com.meproject.newsstream.data.remote.dto.auth.login.RefreshRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * An interface representing the authentication API endpoints.
 */
interface AuthApi {
    @POST("login/")
    suspend fun login(
        @Body loginDetailsDto: LoginDetailsDto
    ): LoginResponse

    @POST("refresh/")
    fun refreshToken(
        @Body refreshRequest: RefreshRequest
    ): Call<LoginResponse>
}
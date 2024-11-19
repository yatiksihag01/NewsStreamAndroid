package com.meproject.newsstream.data.remote.auth

import com.meproject.newsstream.data.remote.api.AuthApi
import com.meproject.newsstream.data.remote.dto.auth.login.LoginDetailsDto
import com.meproject.newsstream.data.remote.dto.auth.login.LoginResponse
import com.meproject.newsstream.data.remote.dto.auth.login.RefreshRequest
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Responsible for fetching access and refresh tokens from the remote data source.
 */
class RemoteTokenDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    /**
     * Fetches the access and refresh tokens from the remote data source.
     *
     * @param loginDetailsDto The login details to be used for authentication.
     *
     * @throws HttpException if an HTTP error occurs during the fetch.
     * @throws IOException if an I/O error occurs during the fetch.
     */
    suspend fun fetchToken(loginDetailsDto: LoginDetailsDto): LoginResponse {
        return try {
            authApi.login(loginDetailsDto = loginDetailsDto)
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    /**
     * Refreshes the access and refresh tokens using the provided [RefreshRequest].
     *
     * @param refreshRequest The refresh request containing the refresh token.
     *
     * @throws HttpException if an HTTP error occurs during the refresh.
     * @throws IOException if an I/O error occurs during the refresh.
     */
    fun refreshTokens(refreshRequest: RefreshRequest): Response<LoginResponse> {
        return try {
            val call = authApi.refreshToken(refreshRequest = refreshRequest)
            call.execute()
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }
}
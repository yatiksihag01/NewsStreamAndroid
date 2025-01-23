package com.meproject.newsstream.domain.repository

import com.meproject.newsstream.domain.model.LoginDetails
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    /**
     * This function fetches a new token from the remote data source,
     * saves it to the local data source.
     *
     * @return True if user logged in successfully, false otherwise.
     * @throws Exception If an error occurs during token retrieval, such as a network or Http error.
     */
    suspend fun loginUser(loginDetails: LoginDetails): Boolean

    /**
     * Provides a flow of access tokens.
     *
     * This function emits the current access token as a string.
     * It may emit `null` if no token is currently available.
     * Consumers of this flow should handle the possibility of receiving `null`.
     *
     * @return A flow emitting the current access token (or `null`).
     */
    fun accessToken(): Flow<String?>
}
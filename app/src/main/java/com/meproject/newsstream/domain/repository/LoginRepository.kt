package com.meproject.newsstream.domain.repository

import com.meproject.newsstream.domain.model.LoginDetails

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
     * Checks if the user is currently logged in by verifying if their access token is saved.
     *
     * @return True if the user is logged in, false otherwise.
     */
    fun isLoggedIn(): Boolean
}
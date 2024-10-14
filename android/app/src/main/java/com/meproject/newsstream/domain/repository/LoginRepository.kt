package com.meproject.newsstream.domain.repository

interface LoginRepository {
    /**
     * Retrieves a valid token for authentication.
     *
     * This function first attempts to retrieve a token from the local data source.
     * If a local token is found, it is returned immediately. If no local token is available,
     * the function fetches a new token from theremote data source, saves it to the local data source, and then returns it.
     *
     * @return A valid authentication token.
     * @throws Exception If an error occurs during token retrieval, such as a network error.
     */
    suspend fun getToken(email: String, password: String): String
}
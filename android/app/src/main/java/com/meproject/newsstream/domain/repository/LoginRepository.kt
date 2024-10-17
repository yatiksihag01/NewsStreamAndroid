package com.meproject.newsstream.domain.repository

interface LoginRepository {
    /**
     * Retrieves a valid token for authentication.
     *
     * This function fetches a new token from the remote data source,
     * saves it to the local data source, and then returns it.
     *
     * @return A valid authentication token.
     * @throws Exception If an error occurs during token retrieval, such as a network error.
     */
    suspend fun getTokenFromRemoteAndSave(email: String, password: String): String

    /**
     * Retrieves a locally saved token for authentication.
     *
     * @return A valid authentication token or null if user is not logged in
     * */
    fun getSavedToken(): String?
}
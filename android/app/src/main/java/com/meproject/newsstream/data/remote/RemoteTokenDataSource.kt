package com.meproject.newsstream.data.remote

import javax.inject.Inject

class RemoteTokenDataSource @Inject constructor() {

    suspend fun fetchToken(email: String, password: String): String {
        TODO("Not yet implemented")
    }

}
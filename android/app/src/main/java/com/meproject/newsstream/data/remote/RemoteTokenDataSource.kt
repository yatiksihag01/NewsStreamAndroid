package com.meproject.newsstream.data.remote

import kotlinx.coroutines.delay
import javax.inject.Inject

class RemoteTokenDataSource @Inject constructor() {

    suspend fun fetchToken(email: String, password: String): String {
        delay(10000)
        return "token"

    }

}
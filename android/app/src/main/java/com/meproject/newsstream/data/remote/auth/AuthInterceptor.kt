package com.meproject.newsstream.data.remote.auth

import com.meproject.newsstream.data.local.auth.LocalTokenDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = localTokenDataSource.getAccessToken()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}
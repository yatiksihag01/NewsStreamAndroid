package com.meproject.newsstream.data.repository

import com.meproject.newsstream.data.local.LocalTokenDataSource
import com.meproject.newsstream.data.remote.RemoteTokenDataSource
import com.meproject.newsstream.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor (
    private val remoteTokenDataSource: RemoteTokenDataSource,
    private val localTokenDataSource: LocalTokenDataSource
) : LoginRepository {
    override suspend fun getToken(email: String, password: String): String {
        return try {
            remoteTokenDataSource.fetchToken(email, password).also {
                localTokenDataSource.clearToken()
                localTokenDataSource.saveToken(it)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
package com.meproject.newsstream.data.repository

import com.meproject.newsstream.data.local.auth.LocalTokenDataSource
import com.meproject.newsstream.data.mappers.toLoginDto
import com.meproject.newsstream.data.remote.auth.RemoteTokenDataSource
import com.meproject.newsstream.domain.model.LoginDetails
import com.meproject.newsstream.domain.repository.LoginRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor (
    private val remoteTokenDataSource: RemoteTokenDataSource,
    private val localTokenDataSource: LocalTokenDataSource
) : LoginRepository {
    override suspend fun loginUser(loginDetails: LoginDetails): Boolean {
        try {
            val loginResponse = remoteTokenDataSource.fetchToken(loginDetailsDto = loginDetails.toLoginDto())
            return if (loginResponse.accessToken.isNotEmpty()) {
                localTokenDataSource.saveAccessToken(loginResponse.accessToken)
                localTokenDataSource.saveRefreshToken(loginResponse.refreshToken)
                true
            } else false
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    override fun isLoggedIn(): Boolean {
        return localTokenDataSource.getAccessToken() != null
    }
}
package com.meproject.newsstream.data.auth

import com.meproject.newsstream.data.local.auth.LocalTokenDataSource
import com.meproject.newsstream.data.remote.auth.RemoteTokenDataSource
import com.meproject.newsstream.data.remote.dto.auth.login.RefreshRequest
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okio.Lock
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

/**
 * Responsible for intercepting requests that fail due to an expired or invalid
 * access token, refreshing the token, and retrying the request with the new token.
 *
 * This class ensures that token refresh is done in a thread-safe manner using a [Lock] and
 * Condition to synchronize the process and avoid multiple simultaneous refresh attempts.
 *
 * @param localTokenDataSource An instance of [LocalTokenDataSource] for storing and
 * retrieving access tokens.
 */
class TokenAuthenticator @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource,
    private val remoteTokenDataSource: RemoteTokenDataSource
) : Authenticator {

    private var isRefreshing = AtomicBoolean(false)

    /**
     * A lock used to synchronize access to the token refresh process to avoid race conditions.
     */
    private val lock = Lock()
    private val condition = lock.newCondition()

    override fun authenticate(route: Route?, response: Response): Request? {
        lock.lock()
        try {
            if (isRefreshing.get()) {
                waitForTokenRefresh()
            } else {
                isRefreshing.set(true)
                refreshTokensSynchronously()
                isRefreshing.set(false)
                // Notify all waiting threads that the token refresh is complete.
                condition.signalAll()
            }
            return if (responseCount(response) >= 3) {
                localTokenDataSource.clearTokens()
                null
            } else {
                buildRequest(response)
            }
        } finally {
            lock.unlock()
        }
    }

    /**
     * Blocks the current thread until the token refresh process is complete.
     * Once the token refresh is finished, the method returns and the original request can be retried.
     */
    private fun waitForTokenRefresh() {
        try {
            while (isRefreshing.get()) {
                condition.await()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }

    private fun buildRequest(response: Response): Request? {
        return localTokenDataSource.getAccessToken()?.let {
            response.request.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        }
    }

    /**
     * Calculates the number of retries for the current request.
     * This method checks if the request has already been retried a certain number of times
     * and returns the retry count.
     *
     * @param response The response that triggered the retry.
     * @return The retry count, which limits the number of times the request will be retried.
     */
    private fun responseCount(response: Response): Int {
        var count = 1
        while (response.priorResponse != null && count <= 3) {
            count++
        }
        return count
    }

    /**
     * Refreshes the access and refresh token using the provided [RemoteTokenDataSource].
     * If the refresh is successful, the new tokens are saved in the [LocalTokenDataSource].
     */
    private fun refreshTokensSynchronously() {
        try {
            val response = remoteTokenDataSource.refreshTokens(
                refreshRequest = RefreshRequest(
                    localTokenDataSource.getRefreshToken() ?: ""
                )
            )
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    localTokenDataSource.saveAccessToken(loginResponse.accessToken)
                    localTokenDataSource.saveRefreshToken(loginResponse.refreshToken)
                }
            } else {
                throw Exception("Token refresh failed with code: ${response.code()}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) {
            // Log other potential exceptions e.g. invalid refresh token etc.
            e.printStackTrace()
        }
    }


}
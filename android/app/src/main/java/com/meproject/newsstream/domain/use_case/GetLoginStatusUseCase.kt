package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * Use case to check if the user is logged in.
 *
 * @return True if the user is logged in, false otherwise.
 */
class GetLoginStatusUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(): Boolean = repository.isLoggedIn()
}
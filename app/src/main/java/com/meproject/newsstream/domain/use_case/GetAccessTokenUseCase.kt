package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(): Flow<String?> = repository.accessToken()
}
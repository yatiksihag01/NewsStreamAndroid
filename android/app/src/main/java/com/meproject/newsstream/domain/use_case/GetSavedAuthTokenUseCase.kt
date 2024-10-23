package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.domain.repository.LoginRepository
import javax.inject.Inject

class GetSavedAuthTokenUseCase @Inject constructor (
    private val repository: LoginRepository
) {
    operator fun invoke(): String? {
        return repository.getSavedToken()
    }
}
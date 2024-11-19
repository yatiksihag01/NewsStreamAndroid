package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.LoginDetails
import com.meproject.newsstream.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(loginDetails: LoginDetails): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = loginRepository.loginUser(loginDetails)))
        } catch(e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
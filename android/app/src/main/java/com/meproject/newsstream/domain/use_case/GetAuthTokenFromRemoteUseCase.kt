package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAuthTokenFromRemoteUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = loginRepository.getTokenFromRemoteAndSave(email, password)))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
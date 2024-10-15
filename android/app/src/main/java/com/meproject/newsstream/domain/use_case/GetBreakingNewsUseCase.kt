package com.meproject.newsstream.domain.use_case

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.domain.model.breaking.BreakingNews
import com.meproject.newsstream.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetBreakingNewsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(size: Int): Flow<Resource<List<BreakingNews>>> = flow {
        try {
            emit(Resource.Loading())
            val breakingNews = homeRepository.getBreakingNews(size)
            emit(Resource.Success(breakingNews))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}
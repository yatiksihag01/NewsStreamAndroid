package com.meproject.newsstream.data.repository

import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.data.remote.api.NewsApi
import com.meproject.newsstream.domain.repository.SummaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SummaryRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : SummaryRepository {
    private val summaryItemsMap = mutableMapOf<String, String>()
    override suspend fun getSummary(articleUrl: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            if (summaryItemsMap.containsKey(articleUrl)) {
                emit(Resource.Success(summaryItemsMap[articleUrl]!!))
            } else {
                val summaryResponse = newsApi.getSummary(articleUrl)
                summaryItemsMap[articleUrl] = summaryResponse.summary
                emit(Resource.Success(summaryResponse.summary))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
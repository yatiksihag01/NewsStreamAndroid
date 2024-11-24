package com.meproject.newsstream.data.repository

import com.meproject.newsstream.common.Constants.ITEMS_PER_PAGE
import com.meproject.newsstream.common.Resource
import com.meproject.newsstream.data.mappers.toSearchedArticle
import com.meproject.newsstream.data.remote.api.NewsApi
import com.meproject.newsstream.domain.model.Category
import com.meproject.newsstream.domain.model.SearchedArticle
import com.meproject.newsstream.domain.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MainRepository {
    override suspend fun searchNews(
        query: String
    ): Flow<Resource<List<SearchedArticle>>> = flow {
        try {
            emit(Resource.Loading())
            val searchedArticles = newsApi.search(
                query = query,
                pageSize = ITEMS_PER_PAGE
            ).map { searchedArticleDto ->
                searchedArticleDto.toSearchedArticle()
            }
            emit(Resource.Success(searchedArticles))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }.flowOn(ioDispatcher)
}
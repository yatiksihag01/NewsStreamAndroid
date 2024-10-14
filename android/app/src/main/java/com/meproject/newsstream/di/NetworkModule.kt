package com.meproject.newsstream.di

import com.meproject.newsstream.common.Constants.BASE_URL
import com.meproject.newsstream.data.remote.AuthApi
import com.meproject.newsstream.data.remote.AuthInterceptor
import com.meproject.newsstream.data.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(authInterceptor).build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofitBuilder: Retrofit.Builder): AuthApi =
        retrofitBuilder.build().create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideNewsApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NewsApi =
        retrofitBuilder.client(okHttpClient).build().create(NewsApi::class.java)

}
package com.meproject.newsstream.di

import com.meproject.newsstream.common.Constants.BASE_URL
import com.meproject.newsstream.data.local.auth.LocalTokenDataSource
import com.meproject.newsstream.data.remote.api.AuthApi
import com.meproject.newsstream.data.remote.auth.AuthInterceptor
import com.meproject.newsstream.data.remote.api.NewsApi
import com.meproject.newsstream.data.remote.auth.RemoteTokenDataSource
import com.meproject.newsstream.data.auth.TokenAuthenticator
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
    fun provideOkHttpClientBuilder(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofitBuilder: Retrofit.Builder): AuthApi =
        retrofitBuilder.build().create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideNewsApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NewsApi =
        retrofitBuilder.client(okHttpClient).build().create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideAuthInterceptor(localTokenDataSource: LocalTokenDataSource): AuthInterceptor =
        AuthInterceptor(localTokenDataSource)

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        localTokenDataSource: LocalTokenDataSource,
        remoteTokenDataSource: RemoteTokenDataSource
    ): TokenAuthenticator = TokenAuthenticator(localTokenDataSource, remoteTokenDataSource)

}
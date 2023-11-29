package com.yassir.movies.di

import com.yassir.movies.data.datasources.network.ApiService
import com.yassir.movies.data.datasources.network.ApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiServiceModule {
    @Singleton
    @Binds
    abstract fun bindApiService(apiService: ApiServiceImpl): ApiService
}

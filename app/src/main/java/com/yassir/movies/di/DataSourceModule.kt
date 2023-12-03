package com.yassir.movies.di

import androidx.paging.PagingSource
import com.yassir.movies.data.datasources.network.ApiService
import com.yassir.movies.data.datasources.network.ApiServiceImpl
import com.yassir.movies.data.datasources.paging.MoviesPagingSource
import com.yassir.movies.data.models.RemoteMovies
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindApiService(apiService: ApiServiceImpl): ApiService

    @Singleton
    @Binds
    abstract fun bindMoviesPagingSource(
        moviesPagingSource: MoviesPagingSource
    ): PagingSource<Int, RemoteMovies.RemoteMovie>
}

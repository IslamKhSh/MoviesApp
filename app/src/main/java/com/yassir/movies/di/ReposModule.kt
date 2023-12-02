package com.yassir.movies.di

import com.yassir.movies.data.repositories.MoviesRepoImpl
import com.yassir.movies.domain.repositories.MoviesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReposModule {
    @Singleton
    @Binds
    abstract fun bindMoviesRepo(moviesRepo: MoviesRepoImpl): MoviesRepo
}

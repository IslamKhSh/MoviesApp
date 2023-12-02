package com.yassir.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yassir.movies.data.datasources.network.ApiService
import com.yassir.movies.data.datasources.paging.MoviesPagingSource
import com.yassir.movies.data.models.RemoteMovies
import com.yassir.movies.domain.repositories.MoviesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val moviesPagingSource: MoviesPagingSource
) : MoviesRepo {
    override suspend fun getMoviesList(): Flow<PagingData<RemoteMovies.RemoteMovie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { moviesPagingSource }
        ).flow
    }
}

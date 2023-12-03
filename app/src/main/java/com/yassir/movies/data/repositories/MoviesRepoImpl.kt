package com.yassir.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.yassir.movies.data.datasources.network.ApiService
import com.yassir.movies.data.errorHandling.NetworkingErrorHandler
import com.yassir.movies.data.models.RemoteMovieDetails
import com.yassir.movies.data.models.RemoteMovies
import com.yassir.movies.data.models.Result
import com.yassir.movies.domain.repositories.MoviesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val moviesPagingSource: PagingSource<Int, RemoteMovies.RemoteMovie>,
) : MoviesRepo {
    override suspend fun getMoviesList(): Flow<PagingData<RemoteMovies.RemoteMovie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { moviesPagingSource }
        ).flow
    }

    override suspend fun getMoviesDetails(id: Int): Result<RemoteMovieDetails> {
        return runCatching {
            apiService.getMovieDetails(id)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { NetworkingErrorHandler.catch(it) }
        )
    }
}

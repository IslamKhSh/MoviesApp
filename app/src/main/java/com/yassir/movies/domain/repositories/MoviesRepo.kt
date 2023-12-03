package com.yassir.movies.domain.repositories

import androidx.paging.PagingData
import com.yassir.movies.data.models.RemoteMovieDetails
import com.yassir.movies.data.models.RemoteMovies
import com.yassir.movies.data.models.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {
    suspend fun getMoviesList(): Flow<PagingData<RemoteMovies.RemoteMovie>>
    suspend fun getMoviesDetails(id: Int): Result<RemoteMovieDetails>
}

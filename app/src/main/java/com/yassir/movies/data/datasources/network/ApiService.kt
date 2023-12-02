package com.yassir.movies.data.datasources.network

import com.yassir.movies.data.models.RemoteMovieDetails
import com.yassir.movies.data.models.RemoteMovies

interface ApiService {
    suspend fun getMoviesList(pageNumber: Int): RemoteMovies
    suspend fun getMovieDetails(movieId: Long): RemoteMovieDetails
}

package com.yassir.movies.data.datasources.network

import com.yassir.movies.data.errorHandling.bodyOrError
import com.yassir.movies.data.models.RemoteMovieDetails
import com.yassir.movies.data.models.RemoteMovies
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(private val ktor: HttpClient) : ApiService {
    override suspend fun getMoviesList(pageNumber: Int): RemoteMovies = ktor.get("discover/movie") {
            parameter("include_adult", false)
            parameter("page", pageNumber)
        }.bodyOrError()

    override suspend fun getMovieDetails(movieId: Long): RemoteMovieDetails = ktor.get("movie/$movieId").bodyOrError()
}

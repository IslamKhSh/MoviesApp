package com.yassir.movies.domain.usecases

import com.yassir.movies.data.models.Result
import com.yassir.movies.data.models.data
import com.yassir.movies.data.models.isSucceeded
import com.yassir.movies.domain.repositories.MoviesRepo
import com.yassir.movies.domain.usecases.base.BaseUseCase
import com.yassir.movies.entities.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepo,
    workDispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Result<MovieDetails>>(workDispatcher) {
    override suspend fun run(params: Int): Result<MovieDetails> {
        val result = repository.getMoviesDetails(params)

        return if (result.isSucceeded) {
            val movieDetails =
                MovieDetails(
                    id = result.data.id,
                    image = result.data.posterPath,
                    title = result.data.title,
                    releaseYear = result.data.releaseDate.split("-").first(),
                    description = result.data.overview
                )
            Result.Success(movieDetails)
        } else {
            result as Result.Error
        }
    }
}

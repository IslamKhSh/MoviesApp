package com.yassir.movies.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.yassir.movies.domain.repositories.MoviesRepo
import com.yassir.movies.domain.usecases.base.BaseUseCase
import com.yassir.movies.entities.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesListUseCase @Inject constructor(
    private val repository: MoviesRepo,
    workDispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, Flow<PagingData<Movie>>>(workDispatcher) {
    override suspend fun run(params: Unit): Flow<PagingData<Movie>> {
        return repository.getMoviesList().map { pagingData ->
            pagingData.map { remoteMovie ->
                Movie(
                    id = remoteMovie.id,
                    title = remoteMovie.title,
                    image = remoteMovie.posterPath,
                    releaseYear = remoteMovie.releaseDate.split("-").first()
                )
            }
        }
    }
}

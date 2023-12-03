package com.yassir.movies.domain.usecases

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.yassir.movies.data.models.RemoteMovies
import com.yassir.movies.domain.repositories.MoviesRepo
import com.yassir.movies.utils.CoroutinesTestDispatcherExtension
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class, CoroutinesTestDispatcherExtension::class)
class GetMoviesListUseCaseTest {
    @MockK(relaxed = true)
    lateinit var repository: MoviesRepo

    @MockK(relaxed = true)
    lateinit var remoteMovie: RemoteMovies.RemoteMovie

    private lateinit var useCase: GetMoviesListUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetMoviesListUseCase(repository, Dispatchers.Main)
    }

    @Test
    fun `when call the useCase with repo returns flow of remoteMovies list then the result must be flow of list of movies`() {
        // given
        every { remoteMovie.id } returns 1
        every { remoteMovie.title } returns "title"
        every { remoteMovie.releaseDate } returns "2023-12-01"

        val pagingFlow = flowOf(PagingData.from(listOf(remoteMovie)))
        coEvery { repository.getMoviesList() } returns pagingFlow

        runTest {
            // when
            val result = useCase(Unit)

            // then
            result.asSnapshot().size shouldBeEqualTo pagingFlow.asSnapshot().size
            result.asSnapshot().first().title shouldBeEqualTo pagingFlow.asSnapshot().first().title
        }
    }
}

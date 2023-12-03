package com.yassir.movies.domain.usecases

import com.yassir.movies.data.models.ErrorType
import com.yassir.movies.data.models.RemoteMovieDetails
import com.yassir.movies.data.models.Result
import com.yassir.movies.data.models.data
import com.yassir.movies.domain.repositories.MoviesRepo
import com.yassir.movies.entities.MovieDetails
import com.yassir.movies.utils.CoroutinesTestDispatcherExtension
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class, CoroutinesTestDispatcherExtension::class)
class GetMovieDetailsUseCaseTest {
    @MockK(relaxed = true)
    lateinit var repository: MoviesRepo

    @MockK
    lateinit var remoteMovieDetails: RemoteMovieDetails

    private lateinit var useCase: GetMovieDetailsUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetMovieDetailsUseCase(repository, Dispatchers.Main)
    }

    @Test
    fun `given repo return success result of remoteMovieDetails then the use case must returns success result of MovieDetails`() {
        // given
        every { remoteMovieDetails.id } returns 1
        every { remoteMovieDetails.title } returns "title"
        every { remoteMovieDetails.posterPath } returns "image"
        every { remoteMovieDetails.releaseDate } returns "2023-12-1"
        every { remoteMovieDetails.overview } returns "description"
        coEvery { repository.getMoviesDetails(any(Int::class)) } returns
            Result.Success(
                remoteMovieDetails
            )

        runTest {
            // when
            val result = useCase(1)

            // then
            result shouldBeInstanceOf Result.Success::class
            result.data shouldBeEqualTo MovieDetails(1, "image", "title", "2023", "description")
        }
    }

    @Test
    fun `given repo return error result then the use case must returns the same error`() {
        // given
        val error = Result.Error(ErrorType.Other)
        coEvery { repository.getMoviesDetails(any(Int::class)) } returns error

        runTest {
            // when
            val result = useCase(1)

            // then
            result shouldBe error
        }
    }
}

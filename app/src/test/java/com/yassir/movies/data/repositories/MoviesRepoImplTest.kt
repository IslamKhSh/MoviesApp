package com.yassir.movies.data.repositories

import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import com.yassir.movies.data.datasources.network.ApiService
import com.yassir.movies.data.errorHandling.HttpResponseError
import com.yassir.movies.data.errorHandling.NetworkingErrorHandler
import com.yassir.movies.data.models.ErrorResponse
import com.yassir.movies.data.models.ErrorType
import com.yassir.movies.data.models.RemoteMovieDetails
import com.yassir.movies.data.models.RemoteMovies
import com.yassir.movies.data.models.Result
import com.yassir.movies.data.models.data
import com.yassir.movies.data.models.error
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MoviesRepoImplTest {
    @MockK
    private lateinit var apiService: ApiService

    @MockK(relaxed = true)
    private lateinit var remoteMovie: RemoteMovies.RemoteMovie

    private lateinit var moviesRepo: MoviesRepoImpl

    @BeforeEach
    fun setUp() {
        val moviesPagingSourceFactory = listOf(remoteMovie).asPagingSourceFactory()
        moviesRepo = MoviesRepoImpl(apiService, moviesPagingSourceFactory())
    }

    @Test
    fun `getMoviesList must return correct page from the pageSource`() {
        // given
        every { remoteMovie.id } returns 1
        every { remoteMovie.title } returns "title"
        every { remoteMovie.posterPath } returns "image"

        runTest {
            // when
            val result = moviesRepo.getMoviesList()

            // then
            result.asSnapshot().first() shouldBeEqualTo remoteMovie
            result.asSnapshot().first().id shouldBeEqualTo 1
            result.asSnapshot().first().title shouldBeEqualTo "title"
            result.asSnapshot().first().posterPath shouldBeEqualTo "image"
        }
    }

    @Test
    fun `getMoviesDetails returns success result when api returns a remoteMovieDetails`() {
        // given
        val remoteMovieDetails = mockk<RemoteMovieDetails>(relaxed = true)
        coEvery { apiService.getMovieDetails(any(Int::class)) } returns remoteMovieDetails

        runTest {
            // when
            val result = moviesRepo.getMoviesDetails(1)

            // then
            result shouldBeInstanceOf Result.Success::class
            result.data shouldBe remoteMovieDetails
        }
    }

    @Test
    fun `getMoviesDetails returns error result when api throws a throwable`() {
        // given
        mockkObject(NetworkingErrorHandler)
        coEvery { apiService.getMovieDetails(any(Int::class)) } throws HttpResponseError(404, ErrorResponse())
        every { NetworkingErrorHandler.catch(any(Throwable::class)) } returns Result.Error(ErrorType.NotFound)

        runTest {
            // when
            val result = moviesRepo.getMoviesDetails(1)

            // then
            result shouldBeInstanceOf Result.Error::class
            result.error!!.errorType shouldBe ErrorType.NotFound
        }
    }
}

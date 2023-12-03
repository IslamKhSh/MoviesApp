package com.yassir.movies.ui.screens.movieDetails

import androidx.lifecycle.SavedStateHandle
import com.yassir.movies.data.models.ErrorType
import com.yassir.movies.data.models.Result
import com.yassir.movies.domain.usecases.GetMovieDetailsUseCase
import com.yassir.movies.entities.MovieDetails
import com.yassir.movies.ui.screens.base.BaseUiState
import com.yassir.movies.utils.CoroutinesTestDispatcherExtension
import com.yassir.movies.utils.InstantTaskExecutorExtension
import com.yassir.movies.utils.MOVIE_ID_PARAM
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(
    InstantTaskExecutorExtension::class,
    CoroutinesTestDispatcherExtension::class,
    MockKExtension::class
)
class MovieDetailsViewModelTest {
    @MockK(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var viewModel: MovieDetailsViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MovieDetailsViewModel(savedStateHandle, getMovieDetailsUseCase)
    }

    @Test
    fun `when useCase returns success result and getMovieDetails uiState must be updated with the movieDetails`() {
        // given
        val movieDetails =
            MovieDetails(
                id = 1,
                image = "image",
                title = "title",
                releaseYear = "2023",
                description = "description"
            )
        every { savedStateHandle.get<Int>(MOVIE_ID_PARAM) } returns 1
        coEvery { getMovieDetailsUseCase(1) } returns Result.Success(movieDetails)

        // when
        runTest { viewModel.getMoviesDetails() }

        // then
        viewModel.uiState.value shouldBeInstanceOf BaseUiState.Success::class
        viewModel.uiState.value shouldBeEqualTo BaseUiState.Success(movieDetails)
    }

    @Test
    fun `when useCase returns error result and getMovieDetails uiState must be updated with the error`() {
        // given
        every { savedStateHandle.get<Int>(MOVIE_ID_PARAM) } returns 1
        coEvery { getMovieDetailsUseCase(any(Int::class)) } returns Result.Error(errorType = ErrorType.Other)

        // when
        runTest { viewModel.getMoviesDetails() }

        // then
        viewModel.uiState.value shouldBeInstanceOf BaseUiState.Error::class
        viewModel.uiState.value shouldBeEqualTo BaseUiState.Error(ErrorType.Other, null)
    }
}

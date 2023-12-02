package com.yassir.movies.ui.screens.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.yassir.movies.domain.usecases.GetMoviesListUseCase
import com.yassir.movies.entities.Movie
import com.yassir.movies.utils.CoroutinesTestDispatcherExtension
import com.yassir.movies.utils.InstantTaskExecutorExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(
    InstantTaskExecutorExtension::class,
    CoroutinesTestDispatcherExtension::class,
    MockKExtension::class
)
class MoviesListViewModelTest {

    @MockK
    private lateinit var getMoviesUseCase: GetMoviesListUseCase

    private lateinit var viewModel: MoviesListViewModel

    @BeforeEach
    fun setup() {
        viewModel = MoviesListViewModel(getMoviesUseCase)
    }

    @Test
    fun `when call getMoviesList use case must be called`() {
        // given
        coEvery { getMoviesUseCase(Unit) } returns flow { PagingData.empty<Movie>() }

        // when
        runTest { viewModel.getMoviesList() }

        // then
        coVerify { getMoviesUseCase(Unit) }
    }
}
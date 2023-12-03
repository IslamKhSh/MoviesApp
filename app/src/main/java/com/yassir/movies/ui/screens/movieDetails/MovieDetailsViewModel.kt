package com.yassir.movies.ui.screens.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yassir.movies.data.models.Result
import com.yassir.movies.domain.usecases.GetMovieDetailsUseCase
import com.yassir.movies.entities.MovieDetails
import com.yassir.movies.ui.screens.base.BaseUiState
import com.yassir.movies.utils.MOVIE_ID_PARAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<BaseUiState<MovieDetails>>(BaseUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val movieId: Int
        get() = savedStateHandle.get<Int>(MOVIE_ID_PARAM)!!

    init {
        getMoviesDetails()
    }

    fun getMoviesDetails() {
        viewModelScope.launch {
            _uiState.value =
                when (val result = getMovieDetailsUseCase(movieId)) {
                    is Result.Error -> BaseUiState.Error(result.errorType, result.errorMessage)
                    is Result.Success -> BaseUiState.Success(result.data)
                }
        }
    }
}

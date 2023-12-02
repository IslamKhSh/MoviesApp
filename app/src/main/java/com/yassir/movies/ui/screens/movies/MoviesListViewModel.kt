package com.yassir.movies.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yassir.movies.domain.usecases.GetMoviesListUseCase
import com.yassir.movies.entities.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesListUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val uiState = _uiState.asStateFlow()

    init {
        getMoviesList()
    }

    fun getMoviesList() {
        viewModelScope.launch {
            getMoviesUseCase(Unit)
                .cachedIn(viewModelScope)
                .collect {
                    _uiState.value = it
                }
        }
    }
}

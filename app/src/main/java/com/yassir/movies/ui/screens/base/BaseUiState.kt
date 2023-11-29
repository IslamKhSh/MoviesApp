package com.yassir.movies.ui.screens.base

import com.yassir.movies.data.datasources.network.responseHandling.ErrorType

sealed class BaseUiState<out T> {
    data object Loading : BaseUiState<Nothing>()
    data class Error(val error: ErrorType) : BaseUiState<Nothing>()
    data class Success<out T>(val data: T) : BaseUiState<T>()
}

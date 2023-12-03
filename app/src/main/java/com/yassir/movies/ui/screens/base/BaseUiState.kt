package com.yassir.movies.ui.screens.base

import com.yassir.movies.data.models.ErrorType

sealed class BaseUiState<out T> {
    data object Loading : BaseUiState<Nothing>()
    data class Error(val error: ErrorType, val errorMsg: String?) : BaseUiState<Nothing>()
    data class Success<out T>(val data: T) : BaseUiState<T>()
}

package com.yassir.movies.data.errorHandling

import com.yassir.movies.data.models.ErrorType

data class PagingError(val errorType: ErrorType, val errorMessage: String? = null) : Throwable()

object PagingErrorHandler : ErrorHandler<PagingError>() {
    override fun catch(throwable: Throwable): PagingError {
        val (errorType, errorMsg) = getErrorTypeWithMsg(throwable)
        return PagingError(errorType, errorMsg)
    }
}

package com.yassir.movies.data.errorHandling

import com.yassir.movies.data.models.Result

object NetworkingErrorHandler : ErrorHandler<Result.Error>() {
    override fun catch(throwable: Throwable): Result.Error {
        val (errorType, errorMsg) = getErrorTypeWithMsg(throwable)
        return Result.Error(errorType, errorMsg)
    }
}

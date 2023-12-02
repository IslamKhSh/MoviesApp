package com.yassir.movies.data.models

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorType: ErrorType, val errorMessage: String? = null) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[type=$errorType, message=$errorMessage]"
        }
    }
}

sealed class ErrorType {
    data object UnAuthorized : ErrorType() // status code 401
    data object NotFound : ErrorType() // status code 404
    data object ServerError : ErrorType() // status code 500

    data object InternetConnection : ErrorType() // IOException
    data object TimeOut : ErrorType() // SocketTimeoutException

    data object Other : ErrorType()
}

/**
 * `true` if [Result] is of type [Result.Success] & holds non-null [Result.Success.data].
 */
val Result<*>.isSucceeded
    get() = this is Result.Success && data != null

val <T> Result<T>.data: T
    get() = (this as Result.Success).data!!

val <T> Result<T>.error: Result.Error?
    get() = this as? Result.Error

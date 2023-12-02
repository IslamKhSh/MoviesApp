package com.yassir.movies.data.errorHandling

import com.yassir.movies.data.models.ErrorResponse
import com.yassir.movies.data.models.ErrorType
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import java.io.IOException
import java.net.SocketTimeoutException

abstract class ErrorHandler<T> {
    abstract fun catch(throwable: Throwable): T
    protected fun getErrorTypeWithMsg(throwable: Throwable): Pair<ErrorType, String?> {
        return when (throwable) {
            is HttpResponseError -> getNetworkingError(throwable) to throwable.error.statusMessage
            is SocketTimeoutException -> ErrorType.TimeOut to null
            is IOException -> ErrorType.InternetConnection to null
            else -> ErrorType.Other to null
        }
    }

    @Suppress("detekt.MagicNumber")
    private fun getNetworkingError(httpResponseError: HttpResponseError): ErrorType {
        return when (httpResponseError.statusCode) {
            401 -> ErrorType.UnAuthorized
            404 -> ErrorType.NotFound
            500 -> ErrorType.ServerError
            else -> ErrorType.Other
        }
    }
}

data class HttpResponseError(val statusCode: Int, val error: ErrorResponse) : Throwable()

@Suppress("detekt.MagicNumber")
suspend inline fun <reified T> HttpResponse.bodyOrError(): T {
    if (status.value >= 300) {
        throw HttpResponseError(status.value, body())
    }

    return body()
}

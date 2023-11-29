package com.yassir.movies.data.datasources.network.responseHandling

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorManager {
    data class HttpResponseError(val statusCode : Int, val error: ErrorResponse) : Throwable()


    suspend inline fun <reified T> HttpResponse.bodyOrError() : T {
        if (status.value >= 300){
            throw HttpResponseError(status.value, body())
        }

        return body()
    }


    fun catch(throwable: Throwable): Result.Error {

        return when (throwable) {
            is HttpResponseError -> getNetworkingError(throwable)
            is SocketTimeoutException -> Result.Error(ErrorType.TimeOut)
            is IOException -> Result.Error(ErrorType.InternetConnection)
            else -> Result.Error(ErrorType.Other(null))
        }
    }

    private fun getNetworkingError(httpResponseError: HttpResponseError): Result.Error {
        return when (httpResponseError.statusCode) {
            401 -> Result.Error(ErrorType.UnAuthorized)
            404 -> Result.Error(ErrorType.NotFound(httpResponseError.error.statusMessage))
            500 -> Result.Error(ErrorType.ServerError(httpResponseError.error.statusMessage))
            else -> Result.Error(ErrorType.Other(httpResponseError.error.statusMessage))
        }
    }
}
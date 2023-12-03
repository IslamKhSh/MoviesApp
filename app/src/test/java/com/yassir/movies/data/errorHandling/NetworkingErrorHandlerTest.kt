package com.yassir.movies.data.errorHandling

import com.yassir.movies.data.models.ErrorResponse
import com.yassir.movies.data.models.ErrorType
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.stream.Stream

class NetworkingErrorHandlerTest {
    @ParameterizedTest
    @MethodSource("typeOfErrors")
    fun `given different types of throwable - call catch - then return proper errorType`(
        throwable: Throwable,
        errorType: ErrorType,
    ) {
        // given in params

        // when
        val result = NetworkingErrorHandler.catch(throwable)

        // then
        result.errorType shouldBeEqualTo errorType
    }

    companion object {
        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun typeOfErrors(): Stream<Arguments?>? {
            return Stream.of(
                arguments(HttpResponseError(404, ErrorResponse()), ErrorType.NotFound),
                arguments(HttpResponseError(401, ErrorResponse()), ErrorType.UnAuthorized),
                arguments(HttpResponseError(500, ErrorResponse()), ErrorType.ServerError),
                arguments(SocketTimeoutException(), ErrorType.TimeOut),
                arguments(IOException(), ErrorType.InternetConnection),
                arguments(UnknownError(), ErrorType.Other)
            )
        }
    }
}

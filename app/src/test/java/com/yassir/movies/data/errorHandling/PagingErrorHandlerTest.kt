package com.yassir.movies.data.errorHandling

import com.yassir.movies.data.models.ErrorResponse
import com.yassir.movies.data.models.ErrorType
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.stream.Stream

class PagingErrorHandlerTest {

    @ParameterizedTest
    @MethodSource("typeOfErrors")
    fun `given different types of throwable - call catch - then return proper errorType`(
        throwable: Throwable,
        errorType: ErrorType,
    ) {
        // given in params

        // when
        val result = PagingErrorHandler.catch(throwable)

        // then
        result.errorType shouldBeEqualTo errorType
    }

    companion object {
        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun typeOfErrors(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.arguments(HttpResponseError(404, ErrorResponse()), ErrorType.NotFound),
                Arguments.arguments(
                    HttpResponseError(401, ErrorResponse()),
                    ErrorType.UnAuthorized
                ),
                Arguments.arguments(HttpResponseError(500, ErrorResponse()), ErrorType.ServerError),
                Arguments.arguments(SocketTimeoutException(), ErrorType.TimeOut),
                Arguments.arguments(IOException(), ErrorType.InternetConnection),
                Arguments.arguments(UnknownError(), ErrorType.Other)
            )
        }
    }
}

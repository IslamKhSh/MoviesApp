package com.yassir.movies.domain.usecases.base

import com.yassir.movies.data.datasources.network.responseHandling.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out Type>(
    private val workDispatcher: CoroutineDispatcher,
) {
    abstract suspend fun run(params: Params): Result<Type>

    suspend operator fun invoke(params: Params): Result<Type> = withContext(workDispatcher) {
            run(params)
        }
}

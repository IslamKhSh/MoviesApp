package com.yassir.movies.domain.usecases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out Type>(
    private val workDispatcher: CoroutineDispatcher,
) {
    abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params): Type = withContext(workDispatcher) {
            run(params)
        }
}

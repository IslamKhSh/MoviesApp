package com.yassir.movies.ui.navigation

import com.yassir.movies.utils.MOVIE_ID_PARAM

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String =
        if (params.isEmpty()) {
            route
        } else {
            val builder = StringBuilder(route)
            params.forEach { builder.append("/{$it}") }
            builder.toString()
        }

    operator fun invoke(): String = fullRoute

    data object MoviesList : Destination("MoviesList")
    data object MovieDetails : Destination("MovieDetails", MOVIE_ID_PARAM) {
        operator fun invoke(movieId: Int) = route.appendParams(MOVIE_ID_PARAM to movieId)
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}

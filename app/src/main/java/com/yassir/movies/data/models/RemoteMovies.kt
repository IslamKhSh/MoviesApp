package com.yassir.movies.data.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RemoteMovies(
    val page: Int,
    val results: List<RemoteMovie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
) {
    @Keep
    @Serializable
    data class RemoteMovie(
        val adult: Boolean,
        @SerialName("backdrop_path") val backdropPath: String?,
        @SerialName("genre_ids") val genreIds: List<Int>,
        val id: Int,
        @SerialName("original_language") val originalLanguage: String,
        @SerialName("original_title") val originalTitle: String,
        val overview: String,
        val popularity: Double,
        @SerialName("poster_path") val posterPath: String,
        @SerialName("release_date") val releaseDate: String,
        val title: String,
        val video: Boolean,
        @SerialName("vote_average") val voteAverage: Double,
        @SerialName("vote_count") val voteCount: Int
    )
}

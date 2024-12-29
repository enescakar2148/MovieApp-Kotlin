package com.enescakar.filmfly.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
data class MovieList (
    val dates: Dates,
    val page: Long,
    val results: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long
)

data class Dates (
    val maximum: String,
    val minimum: String
)

data class Movie (
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backgroundDropImageUrl: String,

    @SerializedName("genre_ids")
    val genreIDS: List<Long>,

    @SerializedName("id")
    val id: Long,

    @SerializedName("original_language")
    val originalLanguage: OriginalLanguage,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterImageUrl: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val starPoint: Double,

    @SerializedName("vote_count")
    val voteCount: Long
):Serializable

enum class OriginalLanguage(val value: String) {
    @SerializedName("en") En("en"),
    @SerializedName("es") Es("es"),
    @SerializedName("fi") Fi("fi");
}
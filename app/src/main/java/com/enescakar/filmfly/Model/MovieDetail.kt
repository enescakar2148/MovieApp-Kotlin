package com.enescakar.filmfly.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String?,
    val rating: Float,
    val releaseDate: String,
    val runtime: String,
    val tagline: String,
    val genres: List<Genre>
) 
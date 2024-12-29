package com.enescakar.filmfly.model

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Float,
    val releaseDate: String
) 
package com.enescakar.filmfly.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

data class MovieResponse(
    val results: List<MovieDto>
)

data class MovieDto(
    val id: Int,
    val title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Float,
    val release_date: String?,
    val runtime: Int? = null,
    val genre_ids: List<Int>?,
    val popularity: Float?,
    val vote_count: Int?
)

data class MovieDetailDto(
    val id: Int,
    val title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Float,
    val release_date: String?,
    val runtime: Int?,
    val genres: List<GenreDto>,
    val tagline: String?
)

data class GenreResponse(
    val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)

data class MovieImagesResponse(
    val backdrops: List<MovieImage>,
    val posters: List<MovieImage>
)

data class MovieImage(
    val file_path: String,
    val width: Int,
    val height: Int
)

interface MovieApiService {
    companion object {
        const val API_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNGI3M2ZhNmY3OTk0NzdlMDg5YTI4YjIxM2MxYzg2OCIsIm5iZiI6MTYwNzUyMTY2OC40NjU5OTk4LCJzdWIiOiI1ZmQwZDU4NGY5MGIxOTAwM2Q4Yzg2ZjQiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.hW2OjNFR6PMv0v0vRze21Uts7lL5gUow3jFP0Vi5ZoY"
    }

    @GET("discover/movie")
    suspend fun getMovies(
        @Header("Authorization") token: String,
        @Query("with_genres") genreId: Int? = null,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MovieResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Header("Authorization") token: String,
        @Query("language") language: String = "en-US"
    ): GenreResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Header("Authorization") authorization: String,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto

    @GET("movie/{movieId}/images")
    suspend fun getMovieImages(
        @Path("movieId") movieId: Int,
        @Header("Authorization") authorization: String
    ): MovieImagesResponse
} 
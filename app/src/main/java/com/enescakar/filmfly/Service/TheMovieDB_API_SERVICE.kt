package com.enescakar.filmfly.Service

import com.enescakar.filmfly.Model.CategoryList
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.Model.MovieList
import com.enescakar.filmfly.Model.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDB_API_SERVICE {

    @GET("movie/{movieId}?api_key=a4b73fa6f799477e089a28b213c1c868&append_to_response=videos,images&language=tr")
    fun getMovieInfo(@Path("movieId") movieId: Long?): Single<Movie>

    @GET("movie/{movieId}/similar?api_key=a4b73fa6f799477e089a28b213c1c868&language=tr")
    fun getSimilarMovies(@Path("movieId") movieId: Long?): Single<MovieList>

    @GET("movie/{movieId}/videos?api_key=a4b73fa6f799477e089a28b213c1c868&language=tr")
    fun getMovieVideos(@Path("movieId") movieId: Long?): Single<com.enescakar.filmfly.Model.Video>

    @GET("genre/movie/list?api_key=a4b73fa6f799477e089a28b213c1c868&language=tr")
    fun getCategories(): Single<CategoryList>

    @GET("movie/now_playing?api_key=a4b73fa6f799477e089a28b213c1c868&language=tr")
    fun getInTheVisionMovies(): Single<MovieList>

    @GET("movie/upcoming?api_key=a4b73fa6f799477e089a28b213c1c868&language=tr&region=tr")
    fun getUpCommingMovies(): Single<MovieList>

    @GET("movie/top_rated?api_key=a4b73fa6f799477e089a28b213c1c868&language=tr")
    fun getTopRatedMovies(): Single<MovieList>

    @GET("discover/movie?api_key=a4b73fa6f799477e089a28b213c1c868&with_genres={genresId}")
    fun getSelectedCategoryMovies(@Path("genresId") genresId: Long): Single<MovieList>


}
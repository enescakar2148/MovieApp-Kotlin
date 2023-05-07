package com.enescakar.filmfly.Service

import android.provider.MediaStore
import com.enescakar.filmfly.Model.Category
import com.enescakar.filmfly.Model.CategoryList
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.Model.MovieList
import com.enescakar.filmfly.Model.Photo
import com.enescakar.filmfly.Model.Video
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Service {

    private val BASE_URL = "https://api.themoviedb.org/3/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Gson
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TheMovieDB_API_SERVICE::class.java)

    fun getCategories(): Single<CategoryList> {
        return api.getCategories()
    }
    fun getMovie(id: Long): Single<Movie> {
        return api.getMovieInfo(id)
    }
    fun getMovieVideo(id: Long): Single<Video> {
        return api.getMovieVideos(id)
    }
    fun getInTheVisionMOvies(): Single<MovieList> {
        return api.getInTheVisionMovies()
    }
    fun getVoteAverange(): Single<MovieList> {
        return api.getTopRatedMovies()
    }
    fun getYakinda(): Single<MovieList> {
        return api.getUpCommingMovies()
    }
    fun getBenzer(id: Long): Single<MovieList>{
        return api.getSimilarMovies(id)
    }
}
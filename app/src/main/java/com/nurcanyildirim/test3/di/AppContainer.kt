package com.nurcanyildirim.test3.di

import com.nurcanyildirim.test3.api.MovieApiService
import com.nurcanyildirim.test3.repository.MovieRepository
import com.nurcanyildirim.test3.viewmodel.MovieViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val movieApiService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    val movieRepository: MovieRepository by lazy {
        MovieRepository(movieApiService)
    }

    val movieViewModel: MovieViewModel by lazy {
        MovieViewModel(movieRepository)
    }
} 
package com.enescakar.filmfly.repository

import android.os.Build
import com.enescakar.filmfly.api.MovieApiService
import com.enescakar.filmfly.api.MovieImage
import com.enescakar.filmfly.model.Category
import com.enescakar.filmfly.model.Genre
import com.enescakar.filmfly.model.Movie
import com.enescakar.filmfly.model.MovieDetail
import java.text.SimpleDateFormat
import java.util.Locale

class MovieRepository(private val apiService: MovieApiService) {

    private fun getDeviceLanguage(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale.getDefault().toLanguageTag()
        } else {
            val locale = Locale.getDefault()
            "${locale.language}-${locale.country}"
        }
    }

    suspend fun getMovies(genreId: Int? = null, page: Int = 1): List<Movie> {
        return apiService.getMovies(
            token = MovieApiService.API_TOKEN,
            genreId = genreId,
            language = getDeviceLanguage(),
            page = page
        ).results
            .filter { movieDto ->
                // Only check if data exists
                movieDto.title != null &&
                movieDto.poster_path != null &&
                movieDto.release_date != null
            }
            .map { movieDto ->
                Movie(
                    id = movieDto.id,
                    title = movieDto.title!!,
                    posterUrl = "https://image.tmdb.org/t/p/w500${movieDto.poster_path}",
                    rating = movieDto.vote_average,
                    releaseDate = formatDate(movieDto.release_date!!)
                )
            }
    }

    suspend fun getCategories(): List<Category> {
        return apiService.getGenres(
            token = MovieApiService.API_TOKEN,
            language = getDeviceLanguage()
        ).genres
            .filter { it.name.isNotEmpty() }
            .map { genreDto ->
                Category(
                    id = genreDto.id,
                    name = genreDto.name,
                    isSelected = false
                )
            }.also { categories ->
                if (categories.isNotEmpty()) {
                    categories[0].isSelected = true
                }
            }
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetail {
        val response = apiService.getMovieDetails(
            movieId = movieId,
            authorization = MovieApiService.API_TOKEN,
            language = getDeviceLanguage()
        )
        
        // Only check if required data exists
        if (response.title == null || 
            response.poster_path == null || 
            response.release_date == null) {
            throw IllegalStateException("Required movie data is missing")
        }
        
        return MovieDetail(
            id = response.id,
            title = response.title,
            overview = response.overview ?: "",
            posterUrl = "https://image.tmdb.org/t/p/w500${response.poster_path}",
            backdropUrl = response.backdrop_path?.let { "https://image.tmdb.org/t/p/original$it" },
            rating = response.vote_average,
            releaseDate = formatDate(response.release_date),
            runtime = formatRuntime(response.runtime ?: 0),
            tagline = response.tagline ?: "",
            genres = response.genres.map { Genre(it.id, it.name) }
        )
    }

    suspend fun getMovieImages(movieId: Int): List<MovieImage> {
        val response = apiService.getMovieImages(movieId, MovieApiService.API_TOKEN)
        return response.backdrops.filter { 
            it.file_path != null && 
            it.width > 0 && 
            it.height > 0 
        }
    }

    private fun formatDate(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        return try {
            val date = inputFormat.parse(dateStr)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateStr
        }
    }

    private fun formatRuntime(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h ${remainingMinutes}m"
    }
} 
package com.nurcanyildirim.test3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurcanyildirim.test3.api.MovieImage
import com.nurcanyildirim.test3.model.MovieDetail
import com.nurcanyildirim.test3.repository.MovieRepository
import kotlinx.coroutines.launch

sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(val movieDetail: MovieDetail) : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
}

sealed class MovieImagesState {
    object Loading : MovieImagesState()
    data class Success(val images: List<MovieImage>) : MovieImagesState()
    data class Error(val message: String) : MovieImagesState()
}

class MovieDetailViewModel(
    private val repository: MovieRepository,
    private val movieId: Int
) : ViewModel() {

    private val _movieDetailState = MutableLiveData<MovieDetailState>()
    val movieDetailState: LiveData<MovieDetailState> = _movieDetailState

    private val _movieImagesState = MutableLiveData<MovieImagesState>()
    val movieImagesState: LiveData<MovieImagesState> = _movieImagesState

    init {
        fetchMovieDetails()
        fetchMovieImages()
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            try {
                _movieDetailState.value = MovieDetailState.Loading
                val movieDetail = repository.getMovieDetails(movieId)
                _movieDetailState.value = MovieDetailState.Success(movieDetail)
            } catch (e: Exception) {
                _movieDetailState.value = MovieDetailState.Error(e.message ?: "An error occurred")
            }
        }
    }

    private fun fetchMovieImages() {
        viewModelScope.launch {
            try {
                _movieImagesState.value = MovieImagesState.Loading
                val images = repository.getMovieImages(movieId)
                _movieImagesState.value = MovieImagesState.Success(images)
            } catch (e: Exception) {
                _movieImagesState.value = MovieImagesState.Error(e.message ?: "An error occurred")
            }
        }
    }
} 
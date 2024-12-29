package com.enescakar.filmfly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurcanyildirim.test3.model.Category
import com.nurcanyildirim.test3.model.Movie
import com.nurcanyildirim.test3.repository.MovieRepository
import kotlinx.coroutines.launch

sealed class MovieState {
    object Loading : MovieState()
    object LoadingMore : MovieState()
    data class Success(val movies: List<Movie>) : MovieState()
    data class Error(val message: String) : MovieState()
}

sealed class CategoryState {
    object Loading : CategoryState()
    data class Success(val categories: List<Category>) : CategoryState()
    data class Error(val message: String) : CategoryState()
}

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieState = MutableLiveData<MovieState>()
    val movieState: LiveData<MovieState> = _movieState

    private val _categoryState = MutableLiveData<CategoryState>()
    val categoryState: LiveData<CategoryState> = _categoryState

    private var selectedGenreId: Int? = null
    private var categories: List<Category> = emptyList()
    private var currentPage = 1
    private var isLastPage = false
    private var isLoading = false
    private val movies = mutableListOf<Movie>()

    init {
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            try {
                _categoryState.value = CategoryState.Loading
                categories = repository.getCategories()
                _categoryState.value = CategoryState.Success(categories)
                
                // Get the first category's ID and fetch movies
                if (categories.isNotEmpty()) {
                    selectedGenreId = categories.first().id
                    fetchMovies(true)
                }
            } catch (e: Exception) {
                _categoryState.value = CategoryState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun fetchMovies(isFirstPage: Boolean = false) {
        if (isLoading || (isLastPage && !isFirstPage)) return

        viewModelScope.launch {
            try {
                isLoading = true
                if (isFirstPage) {
                    currentPage = 1
                    isLastPage = false
                    _movieState.value = MovieState.Loading
                } else {
                    _movieState.value = MovieState.LoadingMore
                }

                val newMovies = repository.getMovies(selectedGenreId, currentPage)
                
                if (isFirstPage) {
                    movies.clear()
                }
                
                if (newMovies.isEmpty()) {
                    isLastPage = true
                } else {
                    movies.addAll(newMovies)
                    currentPage++
                }

                _movieState.value = MovieState.Success(movies.toList())
            } catch (e: Exception) {
                _movieState.value = MovieState.Error(e.message ?: "An error occurred")
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreMovies() {
        fetchMovies(false)
    }

    fun onCategorySelected(category: Category) {
        viewModelScope.launch {
            try {
                // Update selection state
                categories = categories.map { it.copy(isSelected = it.id == category.id) }
                _categoryState.value = CategoryState.Success(categories)
                
                // Reset pagination and fetch movies for selected category
                selectedGenreId = category.id
                currentPage = 1
                isLastPage = false
                movies.clear()
                
                fetchMovies(true)
            } catch (e: Exception) {
                _movieState.value = MovieState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
package com.enescakar.filmfly

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurcanyildirim.test3.adapter.CategoryAdapter
import com.nurcanyildirim.test3.adapter.MovieAdapter
import com.nurcanyildirim.test3.di.AppContainer
import com.nurcanyildirim.test3.model.Category
import com.nurcanyildirim.test3.viewmodel.CategoryState
import com.nurcanyildirim.test3.viewmodel.MovieState
import com.nurcanyildirim.test3.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var loadingMoreProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        initViewModel()
        observeViewModel()
    }

    private fun setupViews() {
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView)
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        loadingMoreProgressBar = findViewById(R.id.loadingMoreProgressBar)

        // Setup movies RecyclerView
        movieAdapter = MovieAdapter { movie ->
            startActivity(MovieDetailActivity.newIntent(this, movie.id))
        }
        
        val gridLayoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = movieAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                        viewModel.loadMoreMovies()
                    }
                }
            })
        }

        // Setup categories RecyclerView
        categoryAdapter = CategoryAdapter { category ->
            viewModel.onCategorySelected(category)
        }
        categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun initViewModel() {
        val repository = AppContainer().movieRepository
        viewModel = MovieViewModel(repository)
    }

    private fun observeViewModel() {
        viewModel.movieState.observe(this) { state ->
            when (state) {
                is MovieState.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                    loadingMoreProgressBar.visibility = View.GONE
                }
                is MovieState.LoadingMore -> {
                    loadingProgressBar.visibility = View.GONE
                    loadingMoreProgressBar.visibility = View.VISIBLE
                }
                is MovieState.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    loadingMoreProgressBar.visibility = View.GONE
                    movieAdapter.updateMovies(state.movies)
                }
                is MovieState.Error -> {
                    loadingProgressBar.visibility = View.GONE
                    loadingMoreProgressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.categoryState.observe(this) { state ->
            when (state) {
                is CategoryState.Loading -> {
                    // Show loading state
                }
                is CategoryState.Success -> {
                    categoryAdapter.updateCategories(state.categories)
                }
                is CategoryState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
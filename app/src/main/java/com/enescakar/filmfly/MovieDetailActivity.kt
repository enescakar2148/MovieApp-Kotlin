package com.enescakar.filmfly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.enescakar.filmfly.adapter.MovieImagesAdapter
import com.enescakar.filmfly.auth.AuthProcessActivity
import com.enescakar.filmfly.di.AppContainer
import com.enescakar.filmfly.dialog.ImageViewerDialog
import com.enescakar.filmfly.model.MovieDetail
import com.enescakar.filmfly.viewmodel.MovieDetailState
import com.enescakar.filmfly.viewmodel.MovieDetailViewModel
import com.enescakar.filmfly.viewmodel.MovieImagesState

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var backdropImage: ImageView
    private lateinit var posterImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var taglineText: TextView
    private lateinit var ratingText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var runtimeText: TextView
    private lateinit var genresChipGroup: ChipGroup
    private lateinit var overviewText: TextView
    private lateinit var favoriteButton: FloatingActionButton
    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var imagesAdapter: MovieImagesAdapter

    companion object {
        private const val EXTRA_MOVIE_ID = "extra_movie_id"

        fun newIntent(context: Context, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        if (movieId == -1) {
            finish()
            return
        }

        setupViews()
        initViewModel(movieId)
        observeViewModel()
    }

    private fun setupViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        progressBar = findViewById(R.id.progressBar)
        backdropImage = findViewById(R.id.backdropImage)
        posterImage = findViewById(R.id.posterImage)
        titleText = findViewById(R.id.titleText)
        taglineText = findViewById(R.id.taglineText)
        ratingText = findViewById(R.id.ratingText)
        releaseDateText = findViewById(R.id.releaseDateText)
        runtimeText = findViewById(R.id.runtimeText)
        genresChipGroup = findViewById(R.id.genresChipGroup)
        overviewText = findViewById(R.id.overviewText)
        favoriteButton = findViewById(R.id.favoriteButton)
        imagesRecyclerView = findViewById(R.id.imagesRecyclerView)

        setupImagesRecyclerView()

        favoriteButton.setOnClickListener {
            // TODO: Check if user is logged in
            startActivity(AuthProcessActivity.newIntent(this))
        }
    }

    private fun initViewModel(movieId: Int) {
        val repository = AppContainer().movieRepository
        viewModel = MovieDetailViewModel(repository, movieId)
    }

    private fun observeViewModel() {
        viewModel.movieDetailState.observe(this) { state ->
            when (state) {
                is MovieDetailState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is MovieDetailState.Success -> {
                    progressBar.visibility = View.GONE
                    displayMovieDetails(state.movieDetail)
                }
                is MovieDetailState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.movieImagesState.observe(this) { state ->
            when (state) {
                is MovieImagesState.Loading -> {
                    // You might want to show a loading indicator for images
                }
                is MovieImagesState.Success -> {
                    imagesAdapter.updateImages(state.images)
                }
                is MovieImagesState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun displayMovieDetails(movie: MovieDetail) {
        // Load backdrop image
        movie.backdropUrl?.let {
            Glide.with(this)
                .load(it)
                .into(backdropImage)
        }

        // Load poster image
        Glide.with(this)
            .load(movie.posterUrl)
            .into(posterImage)

        // Set text fields
        titleText.text = movie.title
        taglineText.text = movie.tagline
        ratingText.text = "★".repeat((movie.rating / 2).toInt())
        releaseDateText.text = movie.releaseDate
        runtimeText.text = movie.runtime
        overviewText.text = movie.overview

        // Add genre chips
        genresChipGroup.removeAllViews()
        movie.genres.forEach { genre ->
            val chip = Chip(this).apply {
                text = genre.name
                isClickable = false
                setTextColor(getColor(android.R.color.white))
                setChipBackgroundColorResource(R.color.category_background)
            }
            genresChipGroup.addView(chip)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Setup images RecyclerView
    private fun setupImagesRecyclerView() {
        imagesAdapter = MovieImagesAdapter { imageUrl ->
            ImageViewerDialog.newInstance(imageUrl)
                .show(supportFragmentManager, "image_viewer")
        }
        imagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = imagesAdapter
        }
    }
} 
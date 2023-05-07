package com.enescakar.filmfly.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.enescakar.filmfly.Adapters.BenzerReycylerAdapter
import com.enescakar.filmfly.Adapters.MovieVideoAdapter
import com.enescakar.filmfly.Adapters.YakindaReycylerAdapter
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.R
import com.enescakar.filmfly.ViewModel.FilmDetailsViewModel
import com.enescakar.filmfly.databinding.ActivityFilmDetailBinding

class FilmDetail : AppCompatActivity() {
    private lateinit var binding: ActivityFilmDetailBinding
    private lateinit var viewModel: FilmDetailsViewModel
    private val movieAdapter = MovieVideoAdapter(arrayListOf(), lifecycle)
    private val benzerAdapter = BenzerReycylerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inıtialize ViewModel
        viewModel = FilmDetailsViewModel()
        val movieId = intent.getSerializableExtra("MovieId")

        //get Movie Details DATA
        viewModel.accessData(movieId as Long)

        binding.filmDetailsVideoRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        binding.filmDetailsVideoRecyclerView.adapter = movieAdapter

        binding.filmDetailsBenzerRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        binding.filmDetailsBenzerRecycler.adapter = benzerAdapter
        observeData()
    }

    fun observeData(){
        viewModel.movie.observe(this, Observer {
            val image = "https://image.tmdb.org/t/p/w500/${it.backgroundDropImageUrl}"
            Glide.with(this).load(image).into(binding.filmDetailsImage)
            binding.filmDetailsMovieName.text = it.title
            binding.filmDetailsOverView.text = it.overview

        })
        viewModel.movieVideos.observe(this, Observer {
            movieAdapter.updateVideoAdapter(it.results)
        })
        viewModel.benzerFilmler.observe(this, Observer {
            benzerAdapter.updateBenzerFilmlerAdapter(it)
        })
    }
}
package com.nurcanyildirim.test3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurcanyildirim.test3.R
import com.nurcanyildirim.test3.model.Movie

class MovieAdapter(private val onMovieClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies: List<Movie> = emptyList()

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onMovieClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(
        itemView: View,
        private val onMovieClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val posterImageView: ImageView = itemView.findViewById(R.id.moviePoster)
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val ratingTextView: TextView = itemView.findViewById(R.id.movieRating)
        private val dateTextView: TextView = itemView.findViewById(R.id.movieDate)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            ratingTextView.text = String.format("%.1f", movie.rating)
            dateTextView.text = movie.releaseDate

            Glide.with(itemView.context)
                .load(movie.posterUrl)
                .into(posterImageView)

            itemView.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }
} 
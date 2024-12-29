package com.enescakar.filmfly.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enescakar.filmfly.Model.Category
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.View.FilmDetail
import com.enescakar.filmfly.databinding.InTheVisionRecyclerItemBinding

class InTheVisionRecyclerAdapter(val inTheVisionMovies: ArrayList<Movie>): RecyclerView.Adapter<InTheVisionRecyclerAdapter.MyHolder>() {
    class MyHolder(val binding: InTheVisionRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = InTheVisionRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {

        if (inTheVisionMovies.isEmpty()){
            return inTheVisionMovies.size
        }
        return 5
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        //Poster Image for binding image key and base image url from API
        val poster = "https://image.tmdb.org/t/p/w500/${inTheVisionMovies[position].posterImageUrl}"
        Glide.with(holder.itemView).load(poster).into(holder.binding.inTheVisionFilmImage)

        holder.binding.inTheVisionFilmName.text = inTheVisionMovies[position].title
        holder.binding.inTheVisionFilmDescription.text = inTheVisionMovies[position].overview
        holder.binding.inTheVisionStarPointText.text = inTheVisionMovies[position].starPoint.toString()
        holder.binding.inTheVisionFilmItem.setOnClickListener {
            val intent = Intent(it.context, FilmDetail::class.java)
            intent.putExtra("MovieId", inTheVisionMovies[position].id)
            it.context.startActivity(intent)
        }
    }
    fun updateInTheVisionAdapter(currentMovieList: List<Movie>) {
        inTheVisionMovies.clear()
        inTheVisionMovies.addAll(currentMovieList)
        notifyDataSetChanged()
    }
}
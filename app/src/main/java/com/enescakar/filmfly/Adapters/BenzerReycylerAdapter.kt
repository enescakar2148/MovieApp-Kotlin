package com.enescakar.filmfly.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.View.FilmDetail
import com.enescakar.filmfly.databinding.BenzerRecyclerItemBinding
import com.enescakar.filmfly.databinding.InTheVisionRecyclerItemBinding
import com.enescakar.filmfly.databinding.VoteAverangeRecyclerItemBinding
import com.enescakar.filmfly.databinding.YakindaRecyclerItemBinding

class BenzerReycylerAdapter(val benzerList: ArrayList<Movie>): RecyclerView.Adapter<BenzerReycylerAdapter.MyHolder>() {
    class MyHolder(val binding: BenzerRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = BenzerRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return benzerList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val poster = "https://image.tmdb.org/t/p/w500/${benzerList[position].posterImageUrl}"

        Glide.with(holder.itemView).load(poster).into(holder.binding.benzerImageView)
        holder.binding.benzerMovieName.text = benzerList[position].title
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, FilmDetail::class.java)
            intent.putExtra("MovieId", benzerList.get(position).id)
            it.context.startActivity(intent)
        }

    }
    fun updateBenzerFilmlerAdapter(newCountryList: List<Movie>) {
        benzerList.clear()
        benzerList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}
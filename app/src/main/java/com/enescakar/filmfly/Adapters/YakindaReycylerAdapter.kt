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
import com.enescakar.filmfly.databinding.VoteAverangeRecyclerItemBinding
import com.enescakar.filmfly.databinding.YakindaRecyclerItemBinding

class YakindaReycylerAdapter(val yakindaMovieList: ArrayList<Movie>): RecyclerView.Adapter<YakindaReycylerAdapter.MyHolder>() {
    class MyHolder(val binding: YakindaRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = YakindaRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return yakindaMovieList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val backDrop = "https://image.tmdb.org/t/p/w500/${yakindaMovieList[position]
            .backgroundDropImageUrl}"

        Glide.with(holder.itemView)
            .load(backDrop)
            .into(holder.binding.yakindaImageView)

        holder.binding.yakindaMovieNameText.text = yakindaMovieList[position].title
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, FilmDetail::class.java)
            intent.putExtra("MovieId", yakindaMovieList[position].id)
            it.context.startActivity(intent)
        }

    }
    fun updateYakindaAdapter(newCountryList: List<Movie>) {
        yakindaMovieList.clear()
        yakindaMovieList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}
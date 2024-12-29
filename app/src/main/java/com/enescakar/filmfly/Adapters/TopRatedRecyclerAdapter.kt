package com.enescakar.filmfly.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enescakar.filmfly.Model.Movie
import com.enescakar.filmfly.View.FilmDetail
import com.enescakar.filmfly.databinding.VoteAverangeRecyclerItemBinding

class TopRatedRecyclerAdapter(val voteAverangeList: ArrayList<Movie>): RecyclerView.Adapter<TopRatedRecyclerAdapter.MyHolder>() {
    class MyHolder(val binding: VoteAverangeRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = VoteAverangeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        if (voteAverangeList.isEmpty()){
            return voteAverangeList.size
        }
        return 5
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val backDrop = "https://image.tmdb.org/t/p/w500/${voteAverangeList[position]
            .backgroundDropImageUrl}"

        Glide.with(holder.itemView)
            .load(backDrop)
            .into(holder.binding.voteAverangeImageView)

        holder.binding.voteAverangeMovieNameText.text = voteAverangeList[position].title
        holder.binding.voteAverangeOverViewText.text = voteAverangeList[position].overview
        holder.binding.voteAverangeCard.setOnClickListener { item->
            val intent = Intent(item.context, FilmDetail::class.java)
            intent.putExtra("MovieId", voteAverangeList[position].id)
            item.context.startActivity(intent)
        }
    }
    fun updateVoteAverangeAdapter(newCountryList: List<Movie>) {
        voteAverangeList.clear()
        voteAverangeList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}
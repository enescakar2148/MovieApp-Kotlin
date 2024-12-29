package com.enescakar.filmfly.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.enescakar.filmfly.Model.Result
import com.enescakar.filmfly.databinding.FilmDetailsMovieVideoItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MovieVideoAdapter(val videoList: ArrayList<Result>, val lifeCycle: Lifecycle): RecyclerView.Adapter<MovieVideoAdapter.MyHolder>() {
    class MyHolder(val binding: FilmDetailsMovieVideoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = FilmDetailsMovieVideoItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val id = videoList[position].key
        lifeCycle.addObserver(holder.binding.youtubePlayerView)
        holder.binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                var videoId = id
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }
    fun updateVideoAdapter(newVideoList: List<Result>) {
        videoList.clear()
        videoList.addAll(newVideoList)
        notifyDataSetChanged()
    }
}
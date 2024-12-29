package com.nurcanyildirim.test3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurcanyildirim.test3.R
import com.nurcanyildirim.test3.api.MovieImage

class MovieImagesAdapter(private val onImageClick: (String) -> Unit) : RecyclerView.Adapter<MovieImagesAdapter.ImageViewHolder>() {
    private var images: List<MovieImage> = emptyList()

    fun updateImages(newImages: List<MovieImage>) {
        images = newImages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_image, parent, false)
        return ImageViewHolder(view, onImageClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(
        itemView: View,
        private val onImageClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.movieImage)

        fun bind(movieImage: MovieImage) {
            val imageUrl = "https://image.tmdb.org/t/p/original${movieImage.file_path}"
            
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(imageView)

            itemView.setOnClickListener {
                onImageClick(imageUrl)
            }
        }
    }
} 
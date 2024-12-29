package com.enescakar.filmfly.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.nurcanyildirim.test3.R

class ImageViewerDialog : DialogFragment() {
    companion object {
        private const val ARG_IMAGE_URL = "image_url"

        fun newInstance(imageUrl: String): ImageViewerDialog {
            return ImageViewerDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_URL, imageUrl)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_image_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.fullscreenImage)
        val closeButton = view.findViewById<ImageView>(R.id.closeButton)

        arguments?.getString(ARG_IMAGE_URL)?.let { imageUrl ->
            Glide.with(requireContext())
                .load(imageUrl)
                .into(imageView)
        }

        closeButton.setOnClickListener {
            dismiss()
        }

        imageView.setOnClickListener {
            dismiss()
        }
    }
} 
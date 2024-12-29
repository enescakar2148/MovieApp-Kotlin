package com.enescakar.filmfly.Model

import com.google.gson.annotations.SerializedName

data class Photo(

    @SerializedName("file_path")
    val photoPath: String?,

    @SerializedName("height")
    val height: Int?,

    @SerializedName("height")
    val width: Int?,

) {
}
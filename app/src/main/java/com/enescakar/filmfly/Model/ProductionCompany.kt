package com.enescakar.filmfly.Model

import com.google.gson.annotations.SerializedName

data class ProductionCompany(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("iso_3166_1")
    val name: String?,

) {
}
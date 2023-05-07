package com.enescakar.filmfly.Model

import com.google.gson.annotations.SerializedName



data class CategoryList (
    val genres: List<Category>
)


data class Category (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)

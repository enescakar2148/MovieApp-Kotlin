package com.enescakar.filmfly.model

data class Category(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
) 
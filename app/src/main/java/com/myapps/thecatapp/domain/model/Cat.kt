package com.myapps.thecatapp.domain.model

data class Cat(
    val imageId: String,
    val url: String,
    val breed: Breed? = null,
    val isFavourite: Boolean = false
)

package com.myapps.thecatapp.domain.model

data class Breed(
    val name: String,
    val origin: String,
    val temperament: List<String>,
    val description: String,
    val lifespan: Int?
)
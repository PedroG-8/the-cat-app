package com.myapps.thecatapp.data.model

import com.myapps.thecatapp.domain.model.Breed

data class BreedDto(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String
) {
    fun toEntity() = Breed(
        id = id,
        name = name,
        origin = origin,
        temperament = temperament,
        description = description
    )
}
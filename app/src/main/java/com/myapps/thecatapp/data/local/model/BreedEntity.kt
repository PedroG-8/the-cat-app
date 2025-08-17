package com.myapps.thecatapp.data.local.model

import com.myapps.thecatapp.domain.model.Breed

data class BreedEntity(
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String
) {
    fun toUiModel() = Breed(
        name = name,
        origin = origin,
        temperament = temperament,
        description = description
    )
}

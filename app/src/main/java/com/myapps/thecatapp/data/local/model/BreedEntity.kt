package com.myapps.thecatapp.data.local.model

import com.myapps.thecatapp.domain.model.Breed
import com.myapps.thecatapp.extensions.csvToList

data class BreedEntity(
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val lifespan: Int? = null
) {
    fun toUiModel() = Breed(
        name = name,
        origin = origin,
        temperament = temperament.csvToList(),
        description = description,
        lifespan = lifespan
    )
}

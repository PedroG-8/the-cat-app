package com.myapps.thecatapp.data.remote.model

//import com.myapps.thecatapp.data.local.model.BreedEntity
import com.myapps.thecatapp.data.local.model.BreedEntity

data class BreedDto(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String
) {
    fun toEntity() = BreedEntity(
        name = name,
        origin = origin,
        temperament = temperament,
        description = description
    )
}
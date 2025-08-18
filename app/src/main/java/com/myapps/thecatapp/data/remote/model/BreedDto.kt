package com.myapps.thecatapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.myapps.thecatapp.data.local.model.BreedEntity

data class BreedDto(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    @SerializedName("life_span") val lifespan: String
) {
    fun toEntity() = BreedEntity(
        name = name,
        origin = origin,
        temperament = temperament,
        description = description,
        lifespan = getLifespan(lifespan)
    )

    private fun getLifespan(lifespan: String): Int? {
        runCatching {
            return if ("-" in lifespan) {
                lifespan.split("-")[0].trim().toInt()
            } else {
                lifespan.toInt()
            }
        }
        return null
    }
}
package com.myapps.thecatapp.data.remote.model

import com.myapps.thecatapp.data.local.model.CatEntity

data class CatDto(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<BreedDto>? = null
) {

    fun toEntity() = CatEntity(
        imageId = id,
        url = url,
        breed = breeds?.first()?.toEntity()
    )
}
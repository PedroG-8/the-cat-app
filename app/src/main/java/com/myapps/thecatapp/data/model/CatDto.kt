package com.myapps.thecatapp.data.model

import com.myapps.thecatapp.domain.model.Cat

data class CatDto(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<BreedDto>? = null
) {
    fun toEntity() = Cat(imageId = id, url = url, breed = breeds?.first()?.toEntity())
}
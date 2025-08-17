package com.myapps.thecatapp.data.model

import com.myapps.thecatapp.domain.model.Favourite

data class FavouriteDto(
    val id: String,
    val image: ImageDto
) {
    fun toEntity() = Favourite(id = id, imageId = image.id, imageUrl = image.url)
}

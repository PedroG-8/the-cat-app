package com.myapps.thecatapp.data.remote.model

import com.myapps.thecatapp.domain.model.Favourite

data class FavouriteDto(
    val id: String,
    val image: ImageDto
) {
    fun toUiModel() = Favourite(id = id, imageId = image.id, imageUrl = image.url)
}

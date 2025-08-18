package com.myapps.thecatapp.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapps.thecatapp.domain.model.Cat

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey val imageId: String,
    val url: String,
    val isFavourite: Boolean = false,
    val favouriteId: String? = null,
    @Embedded val breed: BreedEntity? = null
) {
    fun toUiModel() = Cat(
        imageId = imageId,
        url = url,
        isFavourite = isFavourite,
        favouriteId = favouriteId,
        breed = breed?.toUiModel()
    )
}

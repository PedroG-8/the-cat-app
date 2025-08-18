package com.myapps.thecatapp.domain.repository

import com.myapps.thecatapp.data.local.model.CatEntity
import com.myapps.thecatapp.data.remote.model.FavouriteDto

interface CatRepository {
    suspend fun getCatsWithFavourites()
    suspend fun syncFavourites(favourites: List<FavouriteDto>)
    suspend fun addToFavourites(imageId: String): Boolean
    suspend fun removeFromFavourites(imageId: String): Boolean
    suspend fun getCat(imageId: String): CatEntity
}
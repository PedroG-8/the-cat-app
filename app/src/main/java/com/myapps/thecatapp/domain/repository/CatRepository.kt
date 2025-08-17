package com.myapps.thecatapp.domain.repository

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.model.Favourite

interface CatRepository {
    suspend fun getCatsWithFavourites(page: Int): List<Cat>
    suspend fun getFavourites(): List<Favourite>
    suspend fun addToFavourites(imageId: String): Boolean
    suspend fun removeFromFavourites(favouriteId: String): Boolean
}
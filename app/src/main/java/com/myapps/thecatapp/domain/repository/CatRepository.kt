package com.myapps.thecatapp.domain.repository

import com.myapps.thecatapp.data.local.model.CatEntity
import com.myapps.thecatapp.domain.model.Cat

interface CatRepository {
    suspend fun getCatsWithFavourites(page: Int): List<Cat>
    suspend fun getFavourites(): List<Cat>
    suspend fun addToFavourites(imageId: String): Boolean
    suspend fun removeFromFavourites(imageId: String): Boolean
    suspend fun getCat(imageId: String): CatEntity
}
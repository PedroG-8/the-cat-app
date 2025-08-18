package com.myapps.thecatapp.domain.repository

import com.myapps.thecatapp.data.local.model.CatEntity

interface CatRepository {
    suspend fun getCatsWithFavourites()
    suspend fun syncFavourites()
    suspend fun addToFavourites(imageId: String): Boolean
    suspend fun removeFromFavourites(imageId: String): Boolean
    suspend fun getCat(imageId: String): CatEntity
}
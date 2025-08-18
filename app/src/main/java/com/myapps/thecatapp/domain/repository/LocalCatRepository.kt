package com.myapps.thecatapp.domain.repository

import com.myapps.thecatapp.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface LocalCatRepository {
    suspend fun getCatData(imageId: String): Cat?
    suspend fun searchBreed(breed: String): List<Cat>
    fun getAllCats(): Flow<List<Cat>>
    fun getFavouriteCats(): Flow<List<Cat>>
}
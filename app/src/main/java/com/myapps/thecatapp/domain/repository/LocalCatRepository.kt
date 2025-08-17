package com.myapps.thecatapp.domain.repository

import com.myapps.thecatapp.domain.model.Cat

interface LocalCatRepository {
    suspend fun getCatData(imageId: String): Cat?
}
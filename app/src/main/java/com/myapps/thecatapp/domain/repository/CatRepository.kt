package com.myapps.thecatapp.domain.repository

import com.myapps.thecatapp.domain.model.Cat

interface CatRepository {
    suspend fun getRandomCat(): Cat
}
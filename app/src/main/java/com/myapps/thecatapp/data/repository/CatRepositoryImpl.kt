package com.myapps.thecatapp.data.repository

import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatRepositoryImpl(
    private val api: CatApiService
) : CatRepository {
    override suspend fun getCatBreeds(page: Int): List<Cat> {
        return withContext(Dispatchers.IO) {
            api.getCatBreeds(page = page).map { it.toEntity() }
        }
    }
}
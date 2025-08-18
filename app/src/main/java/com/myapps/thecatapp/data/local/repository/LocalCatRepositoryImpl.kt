package com.myapps.thecatapp.data.local.repository

import com.myapps.thecatapp.data.local.CatDao
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.LocalCatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalCatRepositoryImpl(
    private val catDao: CatDao
) : LocalCatRepository {
    override suspend fun getCatData(imageId: String): Cat? {
        return catDao.getCatByImageId(imageId)?.toUiModel()
    }
    override suspend fun searchBreed(breed: String): List<Cat> {
        return catDao.searchBreeds(breed).map { it.toUiModel() }
    }
    override fun getAllCats(): Flow<List<Cat>> =
        catDao.getAllCats().map { it.map { it.toUiModel() } }

    override fun getFavouriteCats(): Flow<List<Cat>> =
        catDao.getFavourites().map { it.map { it.toUiModel() } }
}
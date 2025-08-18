package com.myapps.thecatapp.data.local.repository

import com.myapps.thecatapp.data.local.CatDao
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.LocalCatRepository

class LocalCatRepositoryImpl(
    private val catDao: CatDao
) : LocalCatRepository {
    override suspend fun getCatData(imageId: String): Cat? {
        return catDao.getCatByImageId(imageId)?.toUiModel()
    }

    override suspend fun searchBreed(breed: String): List<Cat> {
        return catDao.searchBreeds(breed).map { it.toUiModel() }
    }

}
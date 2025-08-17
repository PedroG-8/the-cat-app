package com.myapps.thecatapp.data.repository

import com.myapps.thecatapp.data.model.FavouriteRequest
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.model.Favourite
import com.myapps.thecatapp.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CatRepositoryImpl(
    private val api: CatApiService
) : CatRepository {
    override suspend fun getCatsWithFavourites(page: Int): List<Cat> {
        val cats = api.getCatBreeds(page = page).map { it.toEntity() }
        val favouriteImagesId = api.getFavourites().map { it.image.id }

        return cats.map { cat ->
            cat.copy(isFavourite = favouriteImagesId.contains(cat.imageId))
        }
    }

    override suspend fun getFavourites(): List<Favourite> {
        return withContext(Dispatchers.IO) {
            api.getFavourites().map { it.toEntity() }
        }
    }

    override suspend fun addToFavourites(imageId: String): Boolean {
        return runCatching {
            api.addToFavourites(FavouriteRequest(imageId))
            true
        }.getOrElse {
            false
        }
    }
}
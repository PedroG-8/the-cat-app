package com.myapps.thecatapp.data.remote.repository

import com.myapps.thecatapp.data.local.CatDao
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.model.FavouriteRequest
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.model.Favourite
import com.myapps.thecatapp.domain.repository.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val LIMIT = 20
class CatRepositoryImpl(
    private val api: CatApiService,
    private val catDao: CatDao
) : CatRepository {

    override suspend fun getCatsWithFavourites(page: Int): List<Cat> {
        val offset = LIMIT * page
        val cats = api.getCatBreeds(page = page)
        catDao.upsertCats(cats.map { it.toEntity() })

        val favourites = api.getFavourites()
        favourites.forEach { fav ->
            val currentCat = catDao.getCatByImageId(fav.image.id)
            currentCat?.let {
                catDao.updateCat(
                    it.copy(
                        isFavourite = true,
                        favouriteId = fav.id
                    )
                )
            }
        }
        return catDao.getCats(limit = LIMIT, offset = offset).map { it.toUiModel() }
    }

    override suspend fun getFavourites(): List<Favourite> {
        return withContext(Dispatchers.IO) {
            api.getFavourites().map { it.toUiModel() }
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

    override suspend fun removeFromFavourites(favouriteId: String): Boolean {
        return runCatching {
            api.removeFromFavourites(favouriteId)
            true
        }.getOrElse {
            false
        }
    }
}
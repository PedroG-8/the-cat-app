package com.myapps.thecatapp.data.remote.repository

import android.content.Context
import com.myapps.thecatapp.data.local.CatDao
import com.myapps.thecatapp.data.local.model.CatEntity
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.model.FavouriteRequest
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.CatRepository
import com.myapps.thecatapp.extensions.isOnline

private const val LIMIT = 20
class CatRepositoryImpl(
    private val api: CatApiService,
    private val catDao: CatDao,
    private val context: Context
) : CatRepository {

    override suspend fun getCatsWithFavourites(page: Int): List<Cat> {
        val offset = LIMIT * page
        if (context.isOnline()) {
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
        }

        return catDao.getCats(limit = LIMIT, offset = offset).map { it.toUiModel() }
    }

    override suspend fun getFavourites(): List<Cat> {
        if (context.isOnline()) {
            val favourites = api.getFavourites()
            favourites.forEach { fav ->
                val currentCat = catDao.getCatByImageId(fav.image.id)
                if (currentCat != null) {
                    catDao.updateCat(
                        currentCat.copy(
                            isFavourite = true,
                            favouriteId = fav.id
                        )
                    )
                } else {
                    val newCat = getCat(fav.image.id)
                    catDao.upsertCat(
                        newCat.copy(
                            isFavourite = true,
                            favouriteId = fav.id
                        )
                    )
                }
            }
        }
        return catDao.getFavourites().map { it.toUiModel() }
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

    override suspend fun getCat(imageId: String): CatEntity {
        return api.getCat(imageId).toEntity()
    }
}
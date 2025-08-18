package com.myapps.thecatapp.data.remote.repository

import com.myapps.thecatapp.data.local.CatDao
import com.myapps.thecatapp.data.local.CatPreferences
import com.myapps.thecatapp.data.local.model.CatEntity
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.model.FavouriteDto
import com.myapps.thecatapp.data.remote.model.FavouriteRequest
import com.myapps.thecatapp.domain.repository.CatRepository
import com.myapps.thecatapp.extensions.NetworkChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val LIMIT = 20
class CatRepositoryImpl(
    private val api: CatApiService,
    private val catDao: CatDao,
    private val prefs: CatPreferences,
    private val networkChecker: NetworkChecker
) : CatRepository {
    private var currentPage: Int = 0
    private var endReached = false

    init {
        CoroutineScope(Dispatchers.IO).launch {
            prefs.lastPageFlow.collect { savedPage ->
                currentPage = savedPage
            }
        }
    }

    override suspend fun getCatsWithFavourites() {
        if (endReached || !networkChecker.isOnline()) return

        val cats = api.getCatBreeds(page = currentPage)
        val favourites = api.getFavourites()

        if (cats.isEmpty() && favourites.isEmpty()) return

        catDao.upsertCats(cats.map { it.toEntity() })
        syncFavourites(favourites)

        currentPage++
        prefs.saveLastPage(currentPage)
    }

    override suspend fun syncFavourites(favourites: List<FavouriteDto>) {
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

    override suspend fun addToFavourites(imageId: String): Boolean {
        return runCatching {
            val response = api.addToFavourites(FavouriteRequest(imageId))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val cat = catDao.getCatByImageId(imageId)
                    cat?.let {
                        catDao.updateCat(
                            it.copy(
                                isFavourite = true,
                                favouriteId = body.id
                            )
                        )
                    }
                }
            }
            true
        }.getOrElse {
            false
        }
    }

    override suspend fun removeFromFavourites(imageId: String): Boolean {
        return runCatching {
            val cat = catDao.getCatByImageId(imageId)
            cat?.let { cat ->
                cat.favouriteId?.let { api.removeFromFavourites(it) }
                catDao.updateCat(
                    cat.copy(
                        isFavourite = false,
                        favouriteId = null
                    )
                )
            }
            true
        }.getOrElse {
            false
        }
    }

    override suspend fun getCat(imageId: String): CatEntity {
        return api.getCat(imageId).toEntity()
    }
}
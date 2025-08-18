package com.myapps.thecatapp.data.repository

import com.myapps.thecatapp.data.local.CatDao
import com.myapps.thecatapp.data.local.CatPreferences
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.model.CatDto
import com.myapps.thecatapp.data.remote.model.FavouriteDto
import com.myapps.thecatapp.data.remote.model.ImageDto
import com.myapps.thecatapp.data.remote.repository.CatRepositoryImpl
import com.myapps.thecatapp.extensions.NetworkChecker
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class CatRepositoryTest {

    private val api = mockk<CatApiService>(relaxed = true)
    private val dao = mockk<CatDao>(relaxed = true)
    private val prefs = mockk<CatPreferences>(relaxed = true)
    private val networkChecker = mockk<NetworkChecker>(relaxed = true)
    private lateinit var repository: CatRepositoryImpl

    @Before
    fun setUp() {
        repository = CatRepositoryImpl(api, dao, prefs, networkChecker)
    }

    @Test
    fun givenApiReturnsCatBreeds_whenGetCatsWithFavourites_thenListOfCatsInsertedIntoRoom() = runTest {
        val apiBreeds = listOf(
            CatDto(id = "id1", url = "url1"),
            CatDto(id = "id2", url = "url2")
        )
        val favourites = listOf(
            FavouriteDto(id = "id1", image = ImageDto(id = "imgId_1", url = "img_url1"))
        )
        coEvery { networkChecker.isOnline() } returns true
        coEvery { api.getCatBreeds() } returns apiBreeds
        coEvery { api.getFavourites() } returns favourites

        repository.getCatsWithFavourites()

        coVerify { api.getCatBreeds() }
        coVerify { api.getFavourites() }
        coVerify { dao.upsertCats(apiBreeds.map { it.toEntity() }) }
        coVerify { dao.updateCat(any()) }
        coVerify { dao.getCatByImageId(any()) }
    }

    @Test
    fun givenApiReturnsEmptyList_whenGetCatsWithFavourites_thenListOfCatsNotInsertedIntoRoom() = runTest {
        coEvery { networkChecker.isOnline() } returns true
        coEvery { api.getCatBreeds() } returns emptyList()
        coEvery { api.getFavourites() } returns emptyList()

        repository.getCatsWithFavourites()

        coVerify { api.getCatBreeds() }
        coVerify { api.getFavourites() }
        coVerify(exactly = 0) { dao.upsertCats(any()) }
        coVerify(exactly = 0) { dao.updateCat(any()) }
        coVerify(exactly = 0) { dao.getCatByImageId(any()) }
    }

    @Test
    fun givenApiThrowsException_whenGetCatsWithFavourites_thenListOfCatsNotInsertedIntoRoom() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { networkChecker.isOnline() } returns true
        coEvery { api.getCatBreeds() } throws exception
        coEvery { api.getFavourites() } throws exception

        try {
            repository.getCatsWithFavourites()
        } catch (e: RuntimeException) {
            assertEquals("Network error", e.message)
        }

        coVerify { api.getCatBreeds() }
        coVerify(exactly = 0) { api.getFavourites() }
    }
}
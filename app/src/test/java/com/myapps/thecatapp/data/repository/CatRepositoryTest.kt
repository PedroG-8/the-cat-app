package com.myapps.thecatapp.data.repository

import com.myapps.thecatapp.data.local.CatDao
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.model.CatDto
import com.myapps.thecatapp.data.remote.model.FavouriteDto
import com.myapps.thecatapp.data.remote.model.ImageDto
import com.myapps.thecatapp.data.remote.repository.CatRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CatRepositoryTest {

    private val api = mockk<CatApiService>()
    private val dao = mockk<CatDao>()
    private lateinit var repository: CatRepositoryImpl

    @Before
    fun setUp() {
        repository = CatRepositoryImpl(api, dao)
    }

    @Test
    fun givenApiReturnsCatBreeds_whenGetCatBreeds_thenReturnsMappedCatsWithFavouritesList() = runTest {
        val apiBreeds = listOf(
            CatDto(id = "id1", url = "url1", width = 100, height = 100),
            CatDto(id = "id2", url = "url2", width = 120, height = 120)
        )
        val favourites = listOf(
            FavouriteDto(id = "id1", image = ImageDto(id = "imgId_1", url = "img_url1"))
        )
        coEvery { api.getCatBreeds(page = 0) } returns apiBreeds
        coEvery { api.getFavourites() } returns favourites

        val result = repository.getCatsWithFavourites(page = 0)

        assertEquals(apiBreeds.map { it.toUiModel() }, result)
        coVerify { api.getCatBreeds(page = 0) }
        coVerify { api.getFavourites() }
    }

    @Test
    fun givenApiReturnsEmptyList_whenGetCatsWithFavourites_thenReturnsEmptyList() = runTest {
        coEvery { api.getCatBreeds(page = 0) } returns emptyList()
        coEvery { api.getFavourites() } returns emptyList()

        val result = repository.getCatsWithFavourites(page = 0)

        assertTrue(result.isEmpty())
        coVerify { api.getCatBreeds(page = 0) }
        coVerify { api.getFavourites() }
    }

    @Test
    fun givenApiThrowsException_whenGetCatsWithFavourites_thenExceptionPropagates() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { api.getCatBreeds(page = 0) } throws exception
        coEvery { api.getFavourites() } throws exception

        val thrown = assertFailsWith<RuntimeException> {
            repository.getCatsWithFavourites(page = 0)
        }

        assertEquals("Network error", thrown.message)
        coVerify { api.getCatBreeds(page = 0) }
    }
}
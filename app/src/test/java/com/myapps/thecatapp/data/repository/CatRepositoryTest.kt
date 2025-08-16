package com.myapps.thecatapp.data.repository

import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.CatDto
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
    private lateinit var repository: CatRepositoryImpl

    @Before
    fun setUp() {
        repository = CatRepositoryImpl(api)
    }

    @Test
    fun givenApiReturnsCatBreeds_whenGetCatBreeds_thenReturnsMappedCatBreedsList() = runTest {
        val apiBreeds = listOf(
            CatDto(id = "id1", url = "url1", width = 100, height = 100),
            CatDto(id = "id2", url = "url2", width = 120, height = 120)
        )
        coEvery { api.getCatBreeds(page = 0) } returns apiBreeds

        val result = repository.getCatBreeds(page = 0)

        assertEquals(apiBreeds.map { it.toEntity() }, result)
        coVerify { api.getCatBreeds(page = 0) }
    }

    @Test
    fun givenApiReturnsEmptyList_whenGetCatBreeds_thenReturnsEmptyList() = runTest {
        coEvery { api.getCatBreeds(page = 0) } returns emptyList()

        val result = repository.getCatBreeds(page = 0)

        assertTrue(result.isEmpty())
        coVerify { api.getCatBreeds(page = 0) }
    }

    @Test
    fun givenApiThrowsException_whenGetCatBreeds_thenExceptionPropagates() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { api.getCatBreeds(page = 0) } throws exception

        val thrown = assertFailsWith<RuntimeException> {
            repository.getCatBreeds(page = 0)
        }

        assertEquals("Network error", thrown.message)
        coVerify { api.getCatBreeds(page = 0) }
    }
}
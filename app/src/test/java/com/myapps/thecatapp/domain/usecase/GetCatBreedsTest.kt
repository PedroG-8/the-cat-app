package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.CatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetCatBreedsTest {

    private lateinit var getCatBreeds: GetCatBreedsUseCase
    private val repository = mockk<CatRepository>()

    @Before
    fun setUp() {
        getCatBreeds = GetCatBreedsUseCase(repository)
    }

    @Test
    fun givenRepositoryReturnsCatBreeds_whenGetCatBreeds_thenReturnsCatBreedsList() = runTest {
        val fakeBreeds = listOf(
            Cat(id = "id1", url = "url1"),
            Cat(id = "id2", url = "url2")
        )
        coEvery { repository.getCatBreeds(page = 0) } returns fakeBreeds

        val result = getCatBreeds(page = 0).map { it }

        assertEquals(fakeBreeds, result)
        coVerify { repository.getCatBreeds(page = 0) }
    }

    @Test
    fun givenRepositoryReturnsEmptyList_whenGetCatBreeds_thenReturnsEmptyList() = runTest {
        coEvery { repository.getCatBreeds(page = 0) } returns emptyList()

        val result = getCatBreeds(page = 0)

        assertTrue(result.isEmpty())
        coVerify { repository.getCatBreeds(page = 0) }
    }

    @Test
    fun givenRepositoryThrowsException_whenGetCatBreeds_thenExceptionIsMapped() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { repository.getCatBreeds(page = 0) } throws exception

        val thrown = assertFailsWith<RuntimeException> {
            getCatBreeds(page = 0)
        }

        assertEquals("Network error", thrown.message)
        coVerify { repository.getCatBreeds(page = 0) }
    }
}
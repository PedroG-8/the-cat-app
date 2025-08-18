package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.LocalCatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetCatBreedsTest {

    private lateinit var getLocalCats: GetLocalCatsUseCase
    private val repository = mockk<LocalCatRepository>()

    @Before
    fun setUp() {
        getLocalCats = GetLocalCatsUseCase(repository)
    }

    @Test
    fun givenRepositoryReturnsAllCats_whenGetLocalCats_thenReturnsCatList() = runTest {
        val fakeBreeds = listOf(
            Cat(imageId = "id1", url = "url1"),
            Cat(imageId = "id2", url = "url2")
        )
        coEvery { repository.getAllCats() } returns flowOf(fakeBreeds)

        val result = getLocalCats().first()

        assertEquals(fakeBreeds, result)
        coVerify(exactly = 1) { repository.getAllCats() }
    }

    @Test
    fun givenRepositoryReturnsAllCats_whenGetLocalCats_thenReturnsEmptyList() = runTest {
        coEvery { repository.getAllCats() } returns flowOf(emptyList())

        val result = getLocalCats().first()

        assertTrue(result.isEmpty())
        coVerify(exactly = 1) { repository.getAllCats() }
    }

    @Test
    fun givenRepositoryThrowsException_whenGetCatBreeds_thenExceptionIsMapped() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { repository.getAllCats() } throws exception

        val result = assertFailsWith<RuntimeException> {
            getLocalCats().first()
        }

        assertEquals("Network error", result.message)
        coVerify(exactly = 1) { repository.getAllCats() }
    }
}
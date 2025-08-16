package com.myapps.thecatapp.ui.viewmodel

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.GetCatBreedsUseCase
import com.myapps.thecatapp.ui.CatViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CatViewModelTest {

    private lateinit var getCatBreedsUseCase: GetCatBreedsUseCase
    private lateinit var viewModel: CatViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCatBreedsUseCase = mockk()
        viewModel = CatViewModel(getCatBreedsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenUseCaseReturnsBreeds_whenLoadBreeds_thenUpdatesStateFlow() = runTest {
        val fakeBreeds = listOf(
            Cat(id = "id1", url = "url1"),
            Cat(id = "id2", url = "url2")
        )
        coEvery { getCatBreedsUseCase(0) } returns fakeBreeds

        viewModel.loadCatBreeds(page = 0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeBreeds, viewModel.catBreeds.value)
        coVerify { getCatBreedsUseCase(0) }
    }

    @Test
    fun givenUseCaseReturnsEmptyList_whenLoadBreeds_thenStateFlowReturnsEmptyList() = runTest {
        coEvery { getCatBreedsUseCase(0) } returns emptyList()

        viewModel.loadCatBreeds(page = 0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(emptyList(), viewModel.catBreeds.value)
        coVerify { getCatBreedsUseCase(0) }
    }

    @Test
    fun givenUseCaseThrowsException_whenLoadBreeds_thenStateFlowRemainsEmpty() = runTest {
        coEvery { getCatBreedsUseCase(0) } throws RuntimeException("Network error")

        viewModel.loadCatBreeds(page = 0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.catBreeds.value.isEmpty())
        coVerify { getCatBreedsUseCase(0) }
    }
}
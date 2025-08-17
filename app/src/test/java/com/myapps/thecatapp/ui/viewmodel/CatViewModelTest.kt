package com.myapps.thecatapp.ui.viewmodel

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetFavouriteCatsUseCase
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

    private lateinit var getCatsWithFavouritesUseCase: GetCatsWithFavouritesUseCase
    private lateinit var getFavouriteCatsUseCase: GetFavouriteCatsUseCase
    private lateinit var addCatToFavouritesUseCase: AddCatToFavouritesUseCase
    private lateinit var viewModel: CatViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCatsWithFavouritesUseCase = mockk()
        getFavouriteCatsUseCase = mockk()
        addCatToFavouritesUseCase = mockk()
        viewModel = CatViewModel(
            getCatsWithFavouritesUseCase,
            getFavouriteCatsUseCase,
            addCatToFavouritesUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenUseCaseReturnsBreeds_whenLoadBreeds_thenUpdatesStateFlow() = runTest {
        val fakeBreeds = listOf(
            Cat(imageId = "id1", url = "url1"),
            Cat(imageId = "id2", url = "url2")
        )
        coEvery { getCatsWithFavouritesUseCase(0) } returns fakeBreeds

        viewModel.loadCatsWithFavourites(page = 0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeBreeds, viewModel.catBreeds.value)
        coVerify { getCatsWithFavouritesUseCase(0) }
    }

    @Test
    fun givenUseCaseReturnsEmptyList_whenLoadBreeds_thenStateFlowReturnsEmptyList() = runTest {
        coEvery { getCatsWithFavouritesUseCase(0) } returns emptyList()

        viewModel.loadCatsWithFavourites(page = 0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(emptyList(), viewModel.catBreeds.value)
        coVerify { getCatsWithFavouritesUseCase(0) }
    }

    @Test
    fun givenUseCaseThrowsException_whenLoadBreeds_thenStateFlowRemainsEmpty() = runTest {
        coEvery { getCatsWithFavouritesUseCase(0) } throws RuntimeException("Network error")

        viewModel.loadCatsWithFavourites(page = 0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.catBreeds.value.isEmpty())
        coVerify { getCatsWithFavouritesUseCase(0) }
    }
}
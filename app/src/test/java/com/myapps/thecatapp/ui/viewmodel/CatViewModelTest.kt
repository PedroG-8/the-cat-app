package com.myapps.thecatapp.ui.viewmodel

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetLocalCatsUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.SearchBreedUseCase
import com.myapps.thecatapp.ui.screens.CatViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CatViewModelTest {

    private lateinit var getLocalCatsUseCase: GetLocalCatsUseCase
    private lateinit var getCatsWithFavouritesUseCase: GetCatsWithFavouritesUseCase
    private lateinit var addCatToFavouritesUseCase: AddCatToFavouritesUseCase
    private lateinit var searchBreedUseCase: SearchBreedUseCase
    private lateinit var removeCatFromFavouritesUseCase: RemoveCatFromFavouritesUseCase
    private lateinit var viewModel: CatViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getLocalCatsUseCase = mockk(relaxed = true)
        getCatsWithFavouritesUseCase = mockk()
        addCatToFavouritesUseCase = mockk()
        searchBreedUseCase = mockk()
        removeCatFromFavouritesUseCase = mockk()

        viewModel = CatViewModel(
            getLocalCatsUseCase,
            getCatsWithFavouritesUseCase,
            addCatToFavouritesUseCase,
            removeCatFromFavouritesUseCase,
            searchBreedUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenUseCaseReturnsBreeds_whenFlowEmitsBreeds_thenUpdatesStateFlow() = runTest {
        val fakeBreeds = listOf(
            Cat(imageId = "id1", url = "url1"),
            Cat(imageId = "id2", url = "url2")
        )
        coEvery { getLocalCatsUseCase() } returns flowOf(fakeBreeds)

        val viewModel = CatViewModel(
            getLocalCatsUseCase,
            getCatsWithFavouritesUseCase,
            addCatToFavouritesUseCase,
            removeCatFromFavouritesUseCase,
            searchBreedUseCase
        )

        val job = launch { viewModel.catBreeds.collect() }
        val emissions = viewModel.catBreeds.take(2).toList()

        assertEquals(listOf(emptyList(), fakeBreeds), emissions)
        job.cancel()
    }
}
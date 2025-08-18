package com.myapps.thecatapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.SearchBreedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatViewModel(
    private val getCatsWithFavouritesUseCase: GetCatsWithFavouritesUseCase,
    private val addCatToFavouritesUseCase: AddCatToFavouritesUseCase,
    private val removeCatFromFavouritesUseCase: RemoveCatFromFavouritesUseCase,
    private val searchBreedUseCase: SearchBreedUseCase
) : ViewModel() {

    private val _catBreeds = MutableStateFlow<List<Cat>>(emptyList())
    val catBreeds = _catBreeds.asStateFlow()

    private val _searchedBreeds = MutableStateFlow<List<Cat>>(emptyList())
    val searchedBreeds = _searchedBreeds.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var currentPage = 0
    private var endReached = false

    init {
        loadPage()
    }

    fun loadPage() {
        if (endReached || _isLoading.value) return

        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                val nextPage = getCatsWithFavouritesUseCase(page = currentPage)
                if (nextPage.isEmpty()) {
                    endReached = true
                } else {
                    currentPage += 1
                    _catBreeds.value = _catBreeds.value + nextPage
                }
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
                _catBreeds.value = emptyList()

            }
        }
    }

    fun addOrRemoveCatFromFavourites(imageId: String) {
        viewModelScope.launch {
            val currentCat = _catBreeds.value.find { it.imageId == imageId } ?: return@launch
            if (currentCat.isFavourite) {
                currentCat.favouriteId?.let { removeCatFromFavourites(it) }
            } else {
                addCatToFavourites(imageId)
            }
        }
    }

    fun searchBreed(breed: String) {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                if (breed.isEmpty()) {
                    _searchedBreeds.value = emptyList()
                } else {
                    _searchedBreeds.value = searchBreedUseCase(breed)
                }
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
                _searchedBreeds.value = emptyList()
            }
        }
    }

    private fun addCatToFavourites(imageId: String) {
        viewModelScope.launch {
            val success = addCatToFavouritesUseCase(imageId)
            if (success) {
                val updatedList = _catBreeds.value.map { cat ->
                    if (cat.imageId == imageId) {
                        cat.copy(isFavourite = true)
                    } else {
                        cat
                    }
                }
                _catBreeds.value = updatedList
            }
        }
    }

    private fun removeCatFromFavourites(favouriteId: String) {
        viewModelScope.launch {
            val success = removeCatFromFavouritesUseCase(favouriteId)
            if (success) {
                val updatedList = _catBreeds.value.map { cat ->
                    if (cat.favouriteId == favouriteId) {
                        cat.copy(isFavourite = false)
                    } else {
                        cat
                    }
                }
                _catBreeds.value = updatedList
            }
        }
    }
}
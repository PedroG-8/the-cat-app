package com.myapps.thecatapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.model.Favourite
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetFavouriteCatsUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatViewModel(
    private val getCatsWithFavouritesUseCase: GetCatsWithFavouritesUseCase,
    private val getFavouriteCatsUseCase: GetFavouriteCatsUseCase,
    private val addCatToFavouritesUseCase: AddCatToFavouritesUseCase,
    private val removeCatFromFavouritesUseCase: RemoveCatFromFavouritesUseCase
) : ViewModel() {

    private val _catBreeds = MutableStateFlow<List<Cat>>(emptyList())
    val catBreeds = _catBreeds.asStateFlow()

    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites = _favourites.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private var currentPage = 0
    private var endReached = false

    init {
        loadCatsWithFavourites()
    }

    fun loadCatsWithFavourites() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                _catBreeds.value = getCatsWithFavouritesUseCase(page = currentPage)
                _isLoading.value = false
                currentPage += 1
            }.onFailure {
                _catBreeds.value = emptyList()
            }
        }
    }

    fun loadNextPage() {
        if (endReached || _isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            val nextPage = getCatsWithFavouritesUseCase(page = currentPage)
            if (nextPage.isEmpty()) {
                endReached = true
            } else {
                currentPage += 1
                _catBreeds.value = _catBreeds.value + nextPage
            }
            _isLoading.value = false
        }
    }

    fun getFavourites() {
        viewModelScope.launch {
            runCatching {
                _favourites.value = getFavouriteCatsUseCase()
            }.onFailure {
                _favourites.value = emptyList()
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
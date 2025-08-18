package com.myapps.thecatapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetLocalCatsUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.SearchBreedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CatViewModel(
    private val getLocalCatsUseCase: GetLocalCatsUseCase,
    private val getCatsWithFavouritesUseCase: GetCatsWithFavouritesUseCase,
    private val addCatToFavouritesUseCase: AddCatToFavouritesUseCase,
    private val removeCatFromFavouritesUseCase: RemoveCatFromFavouritesUseCase,
    private val searchBreedUseCase: SearchBreedUseCase
) : ViewModel() {

    val catBreeds: StateFlow<List<Cat>> =
        getLocalCatsUseCase()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _searchedBreeds = MutableStateFlow<List<Cat>>(emptyList())
    val searchedBreeds = _searchedBreeds.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadPage() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                getCatsWithFavouritesUseCase()
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

    fun addOrRemoveCatFromFavourites(imageId: String) {
        viewModelScope.launch {
            val currentCat = catBreeds.value.find { it.imageId == imageId } ?: return@launch
            if (currentCat.isFavourite) {
                removeCatFromFavourites(imageId)
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
            addCatToFavouritesUseCase(imageId)
        }
    }

    private fun removeCatFromFavourites(imageId: String) {
        viewModelScope.launch {
            removeCatFromFavouritesUseCase(imageId)
        }
    }
}
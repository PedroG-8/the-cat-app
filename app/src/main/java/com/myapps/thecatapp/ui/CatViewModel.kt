package com.myapps.thecatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.model.Favourite
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetFavouriteCatsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatViewModel(
    private val getCatsWithFavouritesUseCase: GetCatsWithFavouritesUseCase,
    private val getFavouriteCatsUseCase: GetFavouriteCatsUseCase,
    private val addCatToFavouritesUseCase: AddCatToFavouritesUseCase,
) : ViewModel() {

    private val _catBreeds = MutableStateFlow<List<Cat>>(emptyList())
    val catBreeds = _catBreeds.asStateFlow()

    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites = _favourites.asStateFlow()

    fun loadCatsWithFavourites(page: Int) {
        viewModelScope.launch {
            runCatching {
                _catBreeds.value = getCatsWithFavouritesUseCase(page = page)
            }.onFailure {
                _catBreeds.value = emptyList()
            }
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

    fun addCatToFavourites(imageId: String) {
        viewModelScope.launch {
            val success = addCatToFavouritesUseCase(imageId)
            if (success) {
                val updatedList = _catBreeds.value.map { cat ->
                    if (cat.imageId == imageId) {
                        cat.copy(isFavourite = !cat.isFavourite)
                    } else {
                        cat
                    }
                }
                _catBreeds.value = updatedList
            }
        }
    }
}
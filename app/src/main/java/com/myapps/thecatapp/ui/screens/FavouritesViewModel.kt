package com.myapps.thecatapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.GetLocalFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FavouritesViewModel(
    private val getLocalFavouritesUseCase: GetLocalFavouritesUseCase,
    private val removeCatFromFavouritesUseCase: RemoveCatFromFavouritesUseCase
) : ViewModel() {

    val favouriteCatBreeds: StateFlow<List<Cat>> =
        getLocalFavouritesUseCase()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun removeCatFromFavourites(imageId: String) {
        viewModelScope.launch {
            removeCatFromFavouritesUseCase(imageId)
        }
    }
}
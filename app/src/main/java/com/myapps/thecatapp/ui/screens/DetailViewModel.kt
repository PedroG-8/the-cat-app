package com.myapps.thecatapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetLocalCatDataUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DetailViewModel(
    private val getLocalCatDataUseCase: GetLocalCatDataUseCase,
    private val addCatToFavouritesUseCase: AddCatToFavouritesUseCase,
    private val removeCatFromFavouritesUseCase: RemoveCatFromFavouritesUseCase
) : ViewModel() {

    private val _catData = MutableStateFlow<Cat?>(null)
    val catData = _catData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getCatData(imageId: String) {
        viewModelScope.launch {
            runCatching {
                _catData.value = getLocalCatDataUseCase(imageId)
            }.onFailure {
                _catData.value = null
            }
        }
    }

    fun addOrRemoveCatFromFavourites() {
        viewModelScope.launch {
            _catData.value?.let { cat ->
                _isLoading.value = true
                if (cat.isFavourite) {
                     removeCatFromFavourites(cat.imageId)
                } else {
                    addCatToFavourites(cat.imageId)
                }
                _isLoading.value = false
            }
        }
    }

    private fun addCatToFavourites(imageId: String) {
        viewModelScope.launch {
            val success = addCatToFavouritesUseCase(imageId)
            if (success) {
                _catData.value = getLocalCatDataUseCase(imageId)
            }
        }
    }

    private fun removeCatFromFavourites(imageId: String) {
        viewModelScope.launch {
            val success = removeCatFromFavouritesUseCase(imageId)
            if (success) {
                _catData.value = getLocalCatDataUseCase(imageId)
            }
        }
    }
}
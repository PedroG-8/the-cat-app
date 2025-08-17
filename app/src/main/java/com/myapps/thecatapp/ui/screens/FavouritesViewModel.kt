package com.myapps.thecatapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.GetFavouriteCatsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FavouritesViewModel(
    private val getFavouriteCatsUseCase: GetFavouriteCatsUseCase
) : ViewModel() {

    private val _favouriteCats = MutableStateFlow<List<Cat>>(emptyList())
    val favouriteCats = _favouriteCats.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getFavourites()
    }

    private fun getFavourites() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                _favouriteCats.value = getFavouriteCatsUseCase()
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
                _favouriteCats.value = emptyList()
            }
        }
    }

}
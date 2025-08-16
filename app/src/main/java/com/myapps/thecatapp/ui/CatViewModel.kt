package com.myapps.thecatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.GetCatBreedsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatViewModel(
    private val getCatBreeds: GetCatBreedsUseCase
) : ViewModel() {

    private val _catBreeds = MutableStateFlow<List<Cat>>(emptyList())
    val catBreeds = _catBreeds.asStateFlow()

    fun loadCatBreeds(page: Int) {
        viewModelScope.launch {
            runCatching {
                _catBreeds.value = getCatBreeds(page = page)
            }.onFailure {
                _catBreeds.value = emptyList()
            }
        }
    }
}
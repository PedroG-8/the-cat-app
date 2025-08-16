package com.myapps.thecatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.GetRandomCatUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatViewModel(
    private val getRandomCat: GetRandomCatUseCase
) : ViewModel() {

    private val _cat = MutableStateFlow<Cat?>(null)
    val cat = _cat.asStateFlow()

    fun loadCat() {
        viewModelScope.launch {
            _cat.value = getRandomCat()
        }
    }
}
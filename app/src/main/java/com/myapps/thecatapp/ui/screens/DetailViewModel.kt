package com.myapps.thecatapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.usecase.GetLocalCatDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DetailViewModel(
    private val getLocalCatDataUseCase: GetLocalCatDataUseCase
) : ViewModel() {

    private val _catData = MutableStateFlow<Cat?>(null)
    val catData = _catData.asStateFlow()

    fun getCatData(imageId: String) {
        viewModelScope.launch {
            runCatching {
                _catData.value = getLocalCatDataUseCase(imageId)
            }.onFailure {
                _catData.value = null
            }
        }
    }

}
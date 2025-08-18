package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.LocalCatRepository

class SearchBreedUseCase(
    private val localCatRepository: LocalCatRepository
) {
    suspend operator fun invoke(breed: String): List<Cat> {
        return localCatRepository.searchBreed(breed)
    }
}
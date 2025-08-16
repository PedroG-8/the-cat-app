package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.CatRepository

class GetCatBreedsUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(page: Int): List<Cat> {
        return repository.getCatBreeds(page = page)
    }
}
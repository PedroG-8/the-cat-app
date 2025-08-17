package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.CatRepository

class GetCatsWithFavouritesUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(page: Int): List<Cat> {
        return repository.getCatsWithFavourites(page = page)
    }
}
package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.repository.CatRepository

class GetCatsWithFavouritesUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke() {
        return repository.getCatsWithFavourites()
    }
}
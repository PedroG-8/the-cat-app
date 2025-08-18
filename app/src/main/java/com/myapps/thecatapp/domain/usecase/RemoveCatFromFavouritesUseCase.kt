package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.repository.CatRepository

class RemoveCatFromFavouritesUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(imageId: String): Boolean {
        return repository.removeFromFavourites(imageId)
    }
}
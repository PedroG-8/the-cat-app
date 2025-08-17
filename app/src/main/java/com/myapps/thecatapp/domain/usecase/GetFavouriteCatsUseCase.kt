package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Favourite
import com.myapps.thecatapp.domain.repository.CatRepository

class GetFavouriteCatsUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(): List<Favourite> {
        return repository.getFavourites()
    }
}
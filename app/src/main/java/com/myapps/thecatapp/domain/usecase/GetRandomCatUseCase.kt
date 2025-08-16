package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.CatRepository

class GetRandomCatUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(): Cat {
        return repository.getRandomCat()
    }
}
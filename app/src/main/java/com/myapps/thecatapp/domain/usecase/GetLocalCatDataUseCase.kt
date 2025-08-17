package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.LocalCatRepository

class GetLocalCatDataUseCase(
    private val localCatRepository: LocalCatRepository
) {
    suspend operator fun invoke(imageId: String): Cat? {
        return localCatRepository.getCatData(imageId)
    }
}
package com.myapps.thecatapp.domain.usecase

import com.myapps.thecatapp.domain.model.Cat
import com.myapps.thecatapp.domain.repository.LocalCatRepository
import kotlinx.coroutines.flow.Flow

class GetLocalCatsUseCase(private val repo: LocalCatRepository) {
    operator fun invoke(): Flow<List<Cat>> = repo.getAllCats()
}
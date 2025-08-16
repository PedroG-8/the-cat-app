package com.myapps.thecatapp.di

import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.repository.CatRepositoryImpl
import com.myapps.thecatapp.domain.repository.CatRepository
import com.myapps.thecatapp.domain.usecase.GetRandomCatUseCase
import com.myapps.thecatapp.ui.CatViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(CatApiService::class.java)
    }
}

val repositoryModule = module {
    single<CatRepository> { CatRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { GetRandomCatUseCase(get()) }
}

val viewModelModule = module {
    viewModel { CatViewModel(get()) }
}
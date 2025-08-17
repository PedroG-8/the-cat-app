package com.myapps.thecatapp.di

import androidx.room.Room
import com.myapps.thecatapp.BuildConfig
import com.myapps.thecatapp.data.local.CatDatabase
import com.myapps.thecatapp.data.local.repository.LocalCatRepositoryImpl
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.repository.CatRepositoryImpl
import com.myapps.thecatapp.domain.repository.CatRepository
import com.myapps.thecatapp.domain.repository.LocalCatRepository
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetFavouriteCatsUseCase
import com.myapps.thecatapp.domain.usecase.GetLocalCatDataUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.SearchBreedUseCase
import com.myapps.thecatapp.ui.screens.CatViewModel
import com.myapps.thecatapp.ui.screens.DetailViewModel
import com.myapps.thecatapp.ui.screens.FavouritesViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CatDatabase::class.java,
            "cats.db"
        ).build()
    }
    single {
        get<CatDatabase>().catDao
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("x-api-key", BuildConfig.CAT_API_KEY)
                    .build()
                chain.proceed(newRequest)
            }
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
    single<CatRepository> { CatRepositoryImpl(get(), get(), get()) }
    single<LocalCatRepository> { LocalCatRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { GetCatsWithFavouritesUseCase(get()) }
    factory { GetFavouriteCatsUseCase(get()) }
    factory { AddCatToFavouritesUseCase(get()) }
    factory { RemoveCatFromFavouritesUseCase(get()) }
    factory { GetLocalCatDataUseCase(get()) }
    factory { SearchBreedUseCase(get()) }
}

val viewModelModule = module {
    viewModelOf(::CatViewModel)
    viewModelOf(::FavouritesViewModel)
    viewModelOf(::DetailViewModel)
}
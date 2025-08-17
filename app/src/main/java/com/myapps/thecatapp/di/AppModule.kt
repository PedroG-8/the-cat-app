package com.myapps.thecatapp.di

import androidx.room.Room
import com.myapps.thecatapp.BuildConfig
import com.myapps.thecatapp.data.local.CatDatabase
import com.myapps.thecatapp.data.remote.CatApiService
import com.myapps.thecatapp.data.remote.repository.CatRepositoryImpl
import com.myapps.thecatapp.domain.repository.CatRepository
import com.myapps.thecatapp.domain.usecase.AddCatToFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetCatsWithFavouritesUseCase
import com.myapps.thecatapp.domain.usecase.GetFavouriteCatsUseCase
import com.myapps.thecatapp.domain.usecase.RemoveCatFromFavouritesUseCase
import com.myapps.thecatapp.ui.screens.CatViewModel
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
    single<CatRepository> { CatRepositoryImpl(get(), get()) }
}

val useCaseModule = module {
    factory { GetCatsWithFavouritesUseCase(get()) }
    factory { GetFavouriteCatsUseCase(get()) }
    factory { AddCatToFavouritesUseCase(get()) }
    factory { RemoveCatFromFavouritesUseCase(get()) }
}

val viewModelModule = module {
    viewModelOf(::CatViewModel)
}
package com.myapps.thecatapp

import android.app.Application
import com.myapps.thecatapp.di.networkModule
import com.myapps.thecatapp.di.repositoryModule
import com.myapps.thecatapp.di.useCaseModule
import com.myapps.thecatapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TheCatApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TheCatApp)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
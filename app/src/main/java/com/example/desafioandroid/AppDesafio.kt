package com.example.desafioandroid

import android.app.Application
import com.example.desafioandroid.di.appModules
import com.example.desafioandroid.di.remoteModule
import com.example.desafioandroid.di.repositoryModule
import com.example.desafioandroid.di.uiModule


import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppDesafio: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppDesafio)
            modules(appModules)
        }
    }
}
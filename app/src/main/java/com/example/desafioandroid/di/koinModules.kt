package com.example.desafioandroid.di

import com.example.desafioandroid.repository.GitRepoRepository
import com.example.desafioandroid.repository.GitRepoRepositoryImp
import com.example.desafioandroid.service.RepoService
import com.example.desafioandroid.service.createApi
import com.example.desafioandroid.service.provideHttpClient
import com.example.desafioandroid.service.provideRetrofit
import com.example.desafioandroid.ui.MainViewModel

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainViewModel(get()) }
}

val repositoryModule = module {
    single<GitRepoRepository> { GitRepoRepositoryImp(get()) }
}

val remoteModule = module {
  single { provideHttpClient(androidApplication()) }
    single { provideRetrofit(get()) }
    single { createApi<RepoService>(get()) }


}

val appModules by lazy {
    listOf(remoteModule, repositoryModule, uiModule)
}
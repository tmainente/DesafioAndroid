package com.example.desafioandroid
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.assertion.ViewAssertions.matches

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.desafioandroid.mock.RepositoryMock
import com.example.desafioandroid.repository.GitRepoRepository
import com.example.desafioandroid.ui.MainActivity
import com.example.desafioandroid.ui.MainViewModel
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest: KoinTest {

    val uiModule = module {
        viewModel { MainViewModel(get()) }
    }

    val repositoryModule = module {
        single<GitRepoRepository> { RepositoryMock() }
    }

    @Before
    fun setup(){

        startKoin {
            modules(repositoryModule, uiModule)
        }

    }
    @After
    fun finish(){
        unloadKoinModules(listOf(repositoryModule, uiModule))
        stopKoin()
    }

    @Test
    fun shouldDisplayListItem() {
        launchActivity<MainActivity>().apply {
            onView(withId(R.id.rRepo))
                .check(matches(atPosition(0, hasDescendant(withText("Kotlin")))));
        }
    }





}
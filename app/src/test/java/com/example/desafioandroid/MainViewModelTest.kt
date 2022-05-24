package com.example.desafioandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.desafioandroid.di.appModules
import com.example.desafioandroid.model.Repositories
import com.example.desafioandroid.repository.GitRepoRepository
import com.example.desafioandroid.ui.MainViewModel
import com.example.desafioandroid.util.ApiResult
import com.example.desafioandroid.util.ReturnErrorString
import com.example.desafioandroid.util.UIState
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File


@ExperimentalCoroutinesApi

@RunWith(MockitoJUnitRunner::class)



class MainViewModelTest : KoinTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()



        @Mock
    private lateinit var stateDataObserver: Observer<UIState<Repositories>>
    private lateinit var viewModel : MainViewModel
    @Mock
    lateinit var repo: GitRepoRepository


    private val testDispatcher = TestCoroutineDispatcher()
    private var language = "language:kotlin"
    private var sort = "stars"
    private var page: Int = 1

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repo)
    }

    @Test
    fun `when call getRepository loading called`() = runBlocking {
        viewModel.state.observeForever(stateDataObserver)
        viewModel.getRepo(language, sort, page)

        verify(stateDataObserver).onChanged(UIState.Loading)
    }

    @Test
    fun `when view model getRepository success called`() = runBlocking {
        val resourceName = "sucess.json"
        val classLoader = javaClass.classLoader
        val json: String = File(classLoader.getResource(resourceName).file).readText()
        val repositories = Gson().fromJson(json, Repositories::class.java)
        Mockito.`when`(repo.getRepo(language, sort, page)).thenReturn(ApiResult.Success(repositories))
        viewModel.state.observeForever(stateDataObserver)
        viewModel.getRepo(language, sort, page)
        verify(stateDataObserver).onChanged(UIState.Success(repositories))

    }

    @Test
    fun `when view model getRepository error called`() = runBlocking {
       viewModel.state.observeForever(stateDataObserver)
        val generic = ReturnErrorString.GENERIC_ERROR.msg
        Mockito.`when`(repo.getRepo(language, sort, page)).thenReturn(ApiResult.Error(generic))

        viewModel.getRepo(language, sort, page)
        verify(stateDataObserver).onChanged(UIState.Error(generic))
    }

    @Test
    fun `when view model getRepository return items size 0 called`() = runBlocking {
        viewModel.state.observeForever(stateDataObserver)
        val repositories = Repositories()
        Mockito.`when`(repo.getRepo(language, sort, page)).thenReturn(ApiResult.Success(repositories))
        viewModel.getRepo(language, sort, page)
        verify(stateDataObserver).onChanged(UIState.Error(ReturnErrorString.EMPTY_ERROR.msg))
    }

    @Test
    fun `check modules` ()= runBlocking {
        val application = Mockito.mock(AppDesafio::class.java)
        Mockito.`when`(application.cacheDir).thenReturn(File("/data/data/com.example.desafioandroid/cache/"))
        startKoin {
            androidContext(application)
            modules(appModules)
        }.checkModules()
        unloadKoinModules(appModules)
        stopKoin()
    }

}
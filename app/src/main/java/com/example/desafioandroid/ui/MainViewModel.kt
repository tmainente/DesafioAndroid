package com.example.desafioandroid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafioandroid.model.Repositories
import com.example.desafioandroid.repository.GitRepoRepository
import com.example.desafioandroid.util.ApiResult
import com.example.desafioandroid.util.UIState
import com.example.desafioandroid.util.ReturnErrorString

import kotlinx.coroutines.*

class MainViewModel(private val gitRepoRepository: GitRepoRepository): ViewModel() {

    private val _state = MutableLiveData<UIState<Repositories>>()
    val state:LiveData<UIState<Repositories>> = _state

    init{
        _state.postValue(UIState.Initial)
    }

    fun getRepo(language: String, sort: String, page: Int) {

        CoroutineScope(Dispatchers.Main).launch {
            _state.postValue(UIState.Loading)

            when (val response = gitRepoRepository.getRepo(language, sort, page)) {
                is ApiResult.Success -> {
                    if (response.result.items?.size == 0){
                        _state.postValue(UIState.Error(ReturnErrorString.EMPTY_ERROR.msg))
                    } else {
                        _state.postValue(UIState.Success(response.result))
                    }
                }
                is ApiResult.Error -> {
                    _state.postValue(UIState.Error(response.error))
                }
            }
        }
    }
}
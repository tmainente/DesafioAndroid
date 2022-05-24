package com.example.desafioandroid.util

import android.content.Context
import androidx.lifecycle.MutableLiveData

import retrofit2.Response

sealed class UIState<out T> {
    object Initial : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class Error(val errorMessage: String = "") : UIState<Nothing>()
    data class Success<T>(val result: T) : UIState<T>()



    fun <T : Any?> MutableLiveData<UIState<T>>.toDefault(){
        postValue(Initial)
    }

}
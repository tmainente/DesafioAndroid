package com.example.desafioandroid.util
import retrofit2.Response

sealed class ApiResult<out T> {
    class Error( val error: String) :
        ApiResult<Nothing>()

    class Success<T>(val result: T) : ApiResult<T>()
    class ResourceReturn() {
        fun <T> getResource(response: Response<T>): ApiResult<T> {
            when (response.code()) {
                200 -> {
                    response.body()?.let {
                        return Success(it)
                    }
                }
                else -> {
                    return Error(ReturnErrorString.GENERIC_ERROR.msg)
                }
            }
            return Error(ReturnErrorString.GENERIC_ERROR.msg)
        }
    }

}
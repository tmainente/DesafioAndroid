package com.example.desafioandroid.service

import android.app.Application
import com.example.desafioandroid.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun getURL(): String {
    return when {
        BuildConfig.IS_TESTING.get() -> "http:localhost:8080"
        else -> "https://api.github.com/search/"
    }
}

internal fun provideHttpClient(application: Application) : OkHttpClient {
    return OkHttpClient.Builder()
        .cache(provideCache(application))
        .addInterceptor(Interceptor { chain ->
            try {
                chain.proceed(chain.request());
            } catch (e: Exception) {
                val cacheControl: CacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()

                val offlineRequest = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .build()
                chain.proceed(offlineRequest)
            }
        })
        .build()
}

internal fun provideRetrofit(okHttp: OkHttpClient) = Retrofit.Builder()
    .baseUrl(getURL())
    .client(okHttp)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .build()

internal fun provideCache(application: Application): Cache {
    val cacheSize = 10 * 1024 * 1024
    return Cache(application.cacheDir, cacheSize.toLong())
}

internal inline fun <reified T> createApi(retrofit: Retrofit) = retrofit.create(T::class.java)
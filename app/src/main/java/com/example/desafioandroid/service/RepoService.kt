package com.example.desafioandroid.service

import com.example.desafioandroid.model.Repositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RepoService {

    @GET("repositories")
    suspend fun getRepositories(@QueryMap options: Map<String, String>): Response<Repositories>
}
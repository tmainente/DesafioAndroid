package com.example.desafioandroid.repository


import com.example.desafioandroid.model.Repositories
import com.example.desafioandroid.service.RepoService
import com.example.desafioandroid.util.ApiResult
import com.example.desafioandroid.util.ReturnErrorString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitRepoRepositoryImp(private val repoApi: RepoService): GitRepoRepository  {
    override suspend fun getRepo(language: String, sort: String, page: Int): ApiResult<Repositories> {
        val dispatcher = Dispatchers.IO
          return withContext(dispatcher) {
                try {
                    var options: MutableMap<String, String> = mutableMapOf()
                    options.put("q", language)
                    options.put("sort", sort)
                    options.put("page", page.toString())
                    var response = repoApi.getRepositories(options)
                    ApiResult.ResourceReturn().getResource(response)
                }catch (E: Exception){
                    ApiResult.Error(ReturnErrorString.GENERIC_ERROR.msg)
                }


        }
    }
}

interface GitRepoRepository  {
    suspend fun getRepo(language: String, sort: String, page: Int): ApiResult<Repositories>
}
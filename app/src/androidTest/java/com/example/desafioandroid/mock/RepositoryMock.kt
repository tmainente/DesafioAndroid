package com.example.desafioandroid.mock

import com.example.desafioandroid.model.Items
import com.example.desafioandroid.model.Owner
import com.example.desafioandroid.model.Repositories
import com.example.desafioandroid.repository.GitRepoRepository
import com.example.desafioandroid.util.ApiResult
import java.util.concurrent.TimeUnit

class RepositoryMock: GitRepoRepository {
    override suspend fun getRepo(
        language: String,
        sort: String,
        page: Int
    ): ApiResult<Repositories> {
        val repositories = Repositories(items = arrayListOf(Items(name = "Kotlin", id = 1, owner = Owner(login = "JetBrains"), forks = 500, description = "The KOtlin Programming", stargazersCount = 500)))
        TimeUnit.MILLISECONDS.sleep(10000)
        return ApiResult.Success(repositories)
    }
}
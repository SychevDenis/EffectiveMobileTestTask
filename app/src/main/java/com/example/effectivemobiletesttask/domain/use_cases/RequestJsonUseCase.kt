package com.example.effectivemobiletesttask.domain.use_cases
import com.example.effectivemobiletesttask.domain.RepositoryActivity
import javax.inject.Inject

class RequestJsonUseCase @Inject constructor(private val repositoryActivity: RepositoryActivity) {
    fun runRequest(url: String): String {
        return repositoryActivity.requestJson(url)
    }
}
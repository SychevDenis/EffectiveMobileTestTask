package com.example.effectivemobiletesttask.domain.use_cases

import com.example.effectivemobiletesttask.domain.Repository
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import javax.inject.Inject

class GetNumberVacanciesUseCase
@Inject constructor(private val repositoryActivity: Repository) {
    fun getAll(response: ResponseJson): Int {
        return repositoryActivity.getNumberAllVacancies(response)
    }
    fun getFavorites(response: ResponseJson): Int {
        return repositoryActivity.getNumberFavorites(response)
    }
}
package com.example.effectivemobiletesttask.domain.use_cases

import com.example.effectivemobiletesttask.domain.Repository
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies
import javax.inject.Inject

class UpdateAdapterRvVacanciesUseCase
@Inject constructor(private val repositoryActivity: Repository) {
    fun fragmentMainScreen(response: ResponseJson):  List<Vacancies> {
        return repositoryActivity.updateFragmentMainScreenAdapterRvVacancies(response)
    }
    fun fragmentFavorites(response: ResponseJson):  List<Vacancies> {
        return repositoryActivity.updateFragmentFavoritesAdapterRvVacancies(response)
    }
    fun fragmentMoreVacancies(response: ResponseJson):  List<Vacancies> {
        return repositoryActivity.updateMoreVacanciesAdapterRvVacancies(response)
    }
}
package com.example.effectivemobiletesttask.data

import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.ChoosingDeclension
import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.FilterDataJson
import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.RequestJson
import com.example.effectivemobiletesttask.domain.Repository
import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies

class RepositoryImpl(
    private val requestJson: RequestJson,
    private val choosingDeclension: ChoosingDeclension,
    private val filter: FilterDataJson,
) : Repository {
    override fun requestJson(url: String): String {
        return requestJson.runRequest(url)
    }

    override fun choosingDeclensionButtonView(number: Int): String {
        return choosingDeclension.forButton(number)
    }

    override fun choosingDeclensionTextView(number: Int): String {
        return choosingDeclension.forAllText(number)
    }

    override fun getNumberAllVacancies(response: ResponseJson): Int {
        return filter.getAllNumberVacancies(response)
    }

    override fun getNumberFavorites(response: ResponseJson): Int {
        return filter.getNumberFavorites(response)
    }

    override fun updateFragmentFavoritesAdapterRvVacancies(response: ResponseJson): List<Vacancies> {
        return filter.forRvFavorites(response)
    }

    override fun updateMoreVacanciesAdapterRvVacancies(response: ResponseJson): List<Vacancies> {
        return filter.forRvMoreVacancies(response)
    }

    override fun updateFragmentMainScreenAdapterRvVacancies(response: ResponseJson): List<Vacancies> {
        return  filter.forRvMainScreen(response)
    }
    override fun updateFragmentMainScreenAdapterRvBlockRecommendation(response: ResponseJson): List<Offers> {
        return filter.forBlockRecommendation(response)
    }


}




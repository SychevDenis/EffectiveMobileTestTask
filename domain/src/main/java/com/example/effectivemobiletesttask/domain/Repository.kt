package com.example.effectivemobiletesttask.domain

import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies

interface Repository {
    fun requestJson(url: String): String //запрос json
    fun choosingDeclensionButtonView(number: Int): String //выбор склонения для текста
    fun choosingDeclensionTextView(number: Int): String //выбор склонения для текста
    fun updateFragmentMainScreenAdapterRvBlockRecommendation(response: ResponseJson):List<Offers>
    fun updateFragmentMainScreenAdapterRvVacancies(response: ResponseJson):List<Vacancies>
    fun updateFragmentFavoritesAdapterRvVacancies(response: ResponseJson):List<Vacancies>
    fun updateMoreVacanciesAdapterRvVacancies(response: ResponseJson):List<Vacancies>
    fun getNumberAllVacancies(response: ResponseJson):Int
    fun getNumberFavorites(response: ResponseJson):Int
    }
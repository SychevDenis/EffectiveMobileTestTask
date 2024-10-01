package com.example.effectivemobiletesttask.data.metodsRepositoruImpl

import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies
import javax.inject.Inject

class FilterDataJson @Inject constructor() {
    private fun countingFavorites(json: ResponseJson): List<Vacancies> {
        //выводит только те элементы, где есть лайки
        val listVacancies = mutableListOf<Vacancies>()
        for (item in json.vacancies) {
            if (item.isFavorite == true) {
                listVacancies.add(item)
            }
        }
        return listVacancies
    }

    fun forRvFavorites(json: ResponseJson): List<Vacancies> { //для rvFavorites
        //оставляет только те элементы, где есть лайки
        return countingFavorites(json)
    }

    fun forRvMoreVacancies(json: ResponseJson): List<Vacancies> { //json для rvMoreVacancies
        return json.vacancies
    }

    fun forRvMainScreen(json: ResponseJson): List<Vacancies> {//json для rvMainScreen
        return json.vacancies.take(3)
    }


    fun forBlockRecommendation(json: ResponseJson): List<Offers> {//вывод блока рекомендаций
        return json.offers
    }
    fun getAllNumberVacancies(json: ResponseJson): Int {//вывод количеста всех вакансий
        return json.vacancies.size
    }
    fun getNumberFavorites(json: ResponseJson): Int {//вывод количеста избранных вакансий
        return countingFavorites(json).size
    }
}
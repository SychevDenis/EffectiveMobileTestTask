package com.example.effectivemobiletesttask.presentation

import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies

class FilterDataJson() {
    fun forRvFavorites(json: ResponseJson): List<Vacancies> { //для rvFavorites
        //оставляет только те элементы, где есть лайки
        val listVacancies = mutableListOf<Vacancies>()
        for (item in json.vacancies) {
            if (item.isFavorite == true) {
                listVacancies.add(item)
            }
        }
        return listVacancies
    }

    fun forRvMoreVacancies(json: ResponseJson): List<Vacancies> { //для rvMoreVacancies
        return json.vacancies
    }

    fun forRvMainScreen(json: ResponseJson): List<Vacancies> {//для rvMainScreen
        return json.vacancies.take(3)
    }
    fun setNumberVacancies(json: ResponseJson):Int {//вывод количеста вакансий
        return json.vacancies.size
    }
    fun forBlockRecommendation(json: ResponseJson):List<Offers>{//вывод блока рекомендаций
        return json.offers
    }
}
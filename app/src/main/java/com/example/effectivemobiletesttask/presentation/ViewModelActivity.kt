package com.example.effectivemobiletesttask.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies
import com.example.effectivemobiletesttask.domain.use_cases.ChoosingDeclensionTextUseCase
import com.example.effectivemobiletesttask.domain.use_cases.RequestJsonUseCase
import com.example.effectivemobiletesttask.domain.use_cases.UpdateAdapterRvBlockRecommendationUseCase
import com.example.effectivemobiletesttask.domain.use_cases.UpdateAdapterRvVacanciesUseCase
import com.example.effectivemobiletesttask.domain.use_cases.GetNumberVacanciesUseCase
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ViewModelActivity
@Inject constructor(
    private val requestJsonUseCase: RequestJsonUseCase,
    private val choosingDeclensionTextUseCase: ChoosingDeclensionTextUseCase,
    private val updateAdapterRvVacanciesUseCase: UpdateAdapterRvVacanciesUseCase,
    private val updateAdapterRvBlockRecommendationUseCase: UpdateAdapterRvBlockRecommendationUseCase,
    private val getNumberVacancies: GetNumberVacanciesUseCase
) : ViewModel() {
    val ldJson: MutableLiveData<ResponseJson> = MutableLiveData() //VM Data json
    fun choosingDeclensionButtonView(number: Int): String { //выбор склонения для кнопки
        return choosingDeclensionTextUseCase.buttonView(number)
    }
    fun choosingDeclensionTextView(number: Int): String { //выбор склонения для текста
        return choosingDeclensionTextUseCase.textView(number)
    }
    fun updateFragmentMainScreenAdapterRvBlockRecommendation(response: ResponseJson):
            List<Offers> {//обновить Adapter RvBlockRecommendation FragmentMainScreen
        return updateAdapterRvBlockRecommendationUseCase.fragmentMainScreen(response)
    }
    fun updateFragmentMainScreenAdapterRvVacancies(response: ResponseJson):
            List<Vacancies> {//обновить Adapter RvVacancies  FragmentMainScreen
        return updateAdapterRvVacanciesUseCase.fragmentMainScreen(response)
    }
    fun updateFragmentFavoritesAdapterRvVacancies(response: ResponseJson):
            List<Vacancies> {//обновить Adapter RvVacancies FragmentFavorites
        return updateAdapterRvVacanciesUseCase.fragmentFavorites(response)
    }
    fun updateFragmentMoreVacanciesAdapterRvVacancies(response: ResponseJson):
            List<Vacancies> { //обновить Adapter RvVacancies FragmentMoreVacancies
        return updateAdapterRvVacanciesUseCase.fragmentMoreVacancies(response)
    }
    fun getNumberAllVacancies(response: ResponseJson):
            Int {// Vacancies MainScreen
        return getNumberVacancies.getAll(response)
    }
    fun getNumberVacanciesInFavorites(response: ResponseJson):
            Int {// Vacancies MainScreen
        return getNumberVacancies.getFavorites(response)
    }


    fun favoritesTrueFalse(id: String) {//поиск по id и замена данных в ldJson
        var json = ResponseJson()
        ldJson.value?.let { json = it }
        json.apply {
            for (i in this.vacancies) {
                if (i.id == id) {
                    i.isFavorite = i.isFavorite?.not()
                }
            }
        }
        ldJson.value = json
    }


    fun checkingAvailabilityDataVm(): Boolean {//проверка на наличие данных
        return ldJson.value != null
    }

    private fun requestJson(url: String): Flow<String> { //запросить json
        return flow {
            val result = withContext(Dispatchers.IO) { requestJsonUseCase.runRequest(url) }
            emit(result)
        }
    }

    private fun parseJson(jsonString: String): ResponseJson? { //функция парсинга json
        val gson = Gson()
        return try {
            gson.fromJson(
                jsonString,
                ResponseJson::class.java
            )
        } catch (e: JsonSyntaxException) {
            null //возвращаем null, если произошла ошибка при парсинге
        }
    }

    fun updateDataViewModelJson(url: String): Flow<ResponseJson?> {// Функция для получения и парсинга данных
        return flow {
            requestJson(url).collect { jsonString ->
                emit(parseJson(jsonString)) // Выводим результат парсинга
            }
        }
    }


}

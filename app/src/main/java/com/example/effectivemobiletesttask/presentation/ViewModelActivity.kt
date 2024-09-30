package com.example.effectivemobiletesttask.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ViewModelActivity
@Inject constructor(private val requestJsonUseCase: com.example.effectivemobiletesttask.domain.use_cases.RequestJsonUseCase) :
    ViewModel() {

    private val ldJson: MutableLiveData<com.example.effectivemobiletesttask.domain.pojo.ResponseJson> =
        MutableLiveData()

    fun setLdJson(json: com.example.effectivemobiletesttask.domain.pojo.ResponseJson) {
        ldJson.value = json
    }

    fun getLdJson(): MutableLiveData<com.example.effectivemobiletesttask.domain.pojo.ResponseJson> {
        return ldJson
    }

    private fun requestJson(url: String): Flow<String> { //запросить json
        return flow {
            val result = withContext(Dispatchers.IO) { requestJsonUseCase.runRequest(url) }
            emit(result)
        }
    }

    private fun parseJson(jsonString: String): com.example.effectivemobiletesttask.domain.pojo.ResponseJson? { //функция парсинга json
        val gson = Gson()
        return try {
            gson.fromJson(
                jsonString,
                com.example.effectivemobiletesttask.domain.pojo.ResponseJson::class.java
            )
        } catch (e: JsonSyntaxException) {
            null //возвращаем null, если произошла ошибка при парсинге
        }
    }

    fun updateDataViewModelJson(url: String): Flow<com.example.effectivemobiletesttask.domain.pojo.ResponseJson?> {// Функция для получения и парсинга данных
        return flow {
            requestJson(url).collect { jsonString ->
                emit(parseJson(jsonString)) // Выводим результат парсинга
            }
        }
    }


}

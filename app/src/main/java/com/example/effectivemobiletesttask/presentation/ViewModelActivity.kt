package com.example.effectivemobiletesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.use_cases.RequestJsonUseCase
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ViewModelActivity
@Inject constructor(private val requestJsonUseCase: RequestJsonUseCase) :
    ViewModel() {
    private val ldJson: MutableLiveData<ResponseJson> = MutableLiveData()

    fun setLdJson(json:ResponseJson){
        ldJson.value=json
    }
    private fun requestJson(url: String): Flow<String> { //запросить json
        return flow {
            // emit("Loading...")
            val result = withContext(Dispatchers.IO) { requestJsonUseCase.runRequest(url) }
            emit(result)
        }
    }

    private fun parseJson(jsonString: String): ResponseJson? { //функция парсинга json
        val gson = Gson()
        return try {
            gson.fromJson(jsonString, ResponseJson::class.java)
        } catch (e: JsonSyntaxException) {
            null //возвращаем null, если произошла ошибка при парсинге
        }
    }

    fun fetchData(url: String): Flow<ResponseJson?> { // Функция для получения и парсинга данных
        return flow {
            requestJson(url).collect { jsonString ->
                emit(parseJson(jsonString)) // Выводим результат парсинга
            }
        }
    }
}

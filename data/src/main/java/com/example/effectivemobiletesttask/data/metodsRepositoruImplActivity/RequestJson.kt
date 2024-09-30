package com.example.effectivemobiletesttask.data.metodsRepositoruImplActivity

import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class RequestJson @Inject constructor() {
    fun runRequest(url:String):String {

        // Создаем клиент OkHttp
        val client = OkHttpClient()

        // Создаем запрос
        val request = Request.Builder()
            .url(url)
            .build()

        // Делаем запрос в корутине
        val response = client.newCall(request).execute()

        // Проверяем успешный ли ответ
        if (!response.isSuccessful) {
            return response.message
        }

        // Получаем тело ответа
        val responseBody = response.body

        return try {
            responseBody?.string()?:run{"null"}
        } catch (e: Exception) {
            return "null3"
        }
    }
}
package com.example.effectivemobiletesttask.domain

interface Repository {
    fun requestJson(url: String): String //запрос json
    fun choosingDeclensionText(number: Int): String //выбор склонения для текста
}
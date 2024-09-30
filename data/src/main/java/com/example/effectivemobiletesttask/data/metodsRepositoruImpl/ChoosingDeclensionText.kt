package com.example.effectivemobiletesttask.data.metodsRepositoruImpl


import javax.inject.Inject

class ChoosingDeclensionText @Inject constructor() {
     fun run(number: Int): String {//выбор склонения для текста
        return if (number > 0 && number % 10 == 1 && number != 11)
            "Еще $number вакансия"
        else if (number % 10 in listOf(2, 3, 4) && number != 12 && number != 13 && number != 14) {
            "Еще $number вакансии"
        } else "Еще $number вакансий"
    }
}
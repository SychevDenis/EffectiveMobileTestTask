package com.example.effectivemobiletesttask.data.metodsRepositoruImpl


import javax.inject.Inject

class ChoosingDeclension @Inject constructor() {
    fun forButton(number: Int): String {//выбор склонения для кнопки
        return "Еще ${choiceDeclension(number)}"
    }

    fun forAllText(number: Int): String {//выбор склонения для текста
        // вывода числа вакансий
       return choiceDeclension(number)
    }
    private fun choiceDeclension(number: Int):String{// функция склонения
        return if (number > 0 && number % 10 == 1 && number != 11)
            "$number вакансия"
        else if (number % 10 in listOf(2, 3, 4) && number != 12 && number != 13 && number != 14) {
            "$number вакансии"
        } else "$number вакансий"
    }
}
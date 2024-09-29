package com.example.effectivemobiletesttask.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.presentation.RecyclerViewBlockRecommendations.RVBlockRecommendationsAdapter
import com.example.effectivemobiletesttask.presentation.RecyclerViewBlockRecommendations.RVVacanciesAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMainScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMainScreen : Fragment() {
    private lateinit var rvBlockRecommendations: RecyclerView
    private lateinit var rvVacancies: RecyclerView
    private val adapterBlockRecommendations by lazy { RVBlockRecommendationsAdapter() }
    private val adapterVacancies by lazy { RVVacanciesAdapter() }
    private lateinit var buttonMoreVacancies: android.widget.Button
    private var activityInterface: FragmentMainScreenInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)
           // Устанавливаем менеджер компоновки для обоих RecyclerView
        rvBlockRecommendations = view.findViewById(R.id.rvBlockRecommendationsFragmentMainScreen)
        buttonMoreVacancies = view.findViewById(R.id.buttonMoreVacanciesFragmentMainScreen)
        rvVacancies = view.findViewById(R.id.rvVacanciesFragmentMainScreen)
        rvBlockRecommendations.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvVacancies.layoutManager =
            LinearLayoutManager(requireContext()) // Устанавливаем стандартный layoutManager для вертикальной прокрутки
        rvBlockRecommendations.adapter = adapterBlockRecommendations
        rvVacancies.adapter = adapterVacancies
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentMainScreenInterface) { //реализуем интерфейс с активностью
            activityInterface = context
        }
        else {
            throw RuntimeException("$context activity does not implement the " +
                    "FragmentMainScreenInterface ")
        }
    }
    override fun onResume() {
        super.onResume()
        buttonMoreVacancies.setOnClickListener{//подключаем слушатель кнопки
            activityInterface?.clickButtonMoreVacancies()
        }
        activityInterface?.updateDataFromMainScreen()//запрашиваем данные для фрагмента из сети
    }

    override fun onDetach() {
        super.onDetach()
        activityInterface = null //на всякий пожарный дабы утечек памяти не было
    }
    fun updateDataFragmentMainScreen(response: ResponseJson) { //обновить данные
        adapterBlockRecommendations.updateItems(response.offers)
        adapterVacancies.updateItems(response.vacancies)
        buttonMoreVacancies.text = setTextButtonMoreVacancies(response.vacancies.size)
    }

    private fun setTextButtonMoreVacancies(number: Int): String {//выбор склонения для
        // вывода числа вакансий
        return if (number > 0 && number % 10 == 1 && number != 11)
            "Еще $number вакансия"
        else if (number % 10 in listOf(2, 3, 4) && number != 12 && number != 13 && number != 14) {
            "Еще $number вакансии"
        } else "Еще $number вакансий"
    }
    interface FragmentMainScreenInterface{
        fun clickButtonMoreVacancies()
        fun updateDataFromMainScreen()
    }
}

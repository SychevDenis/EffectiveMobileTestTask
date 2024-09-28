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
import com.example.effectivemobiletesttask.domain.pojo.Button
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
    private lateinit var adapterBlockRecommendations: RVBlockRecommendationsAdapter
    private lateinit var adapterVacancies: RVVacanciesAdapter
    private lateinit var buttonMoreVacancies: android.widget.Button
    private var activityInterface: FragmentMainScreenInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)

        rvBlockRecommendations = view.findViewById(R.id.rvBlockRecommendationsFragmentMainScreen)
        rvVacancies = view.findViewById(R.id.rvVacanciesFragmentMainScreen)
        buttonMoreVacancies = view.findViewById(R.id.buttonMoreVacanciesFragmentMainScreen)

        // Устанавливаем менеджер компоновки для обоих RecyclerView
        rvBlockRecommendations.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvVacancies.layoutManager =
            LinearLayoutManager(requireContext()) // Устанавливаем стандартный layoutManager для вертикальной прокрутки

        adapterBlockRecommendations = RVBlockRecommendationsAdapter()
        adapterVacancies = RVVacanciesAdapter()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonMoreVacancies.setOnClickListener{
            activityInterface?.clickButton()
        }
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

    private fun setTextButtonMoreVacancies(number: Int): String {
        //выбор склонения для текста кнопки
        return if (number > 0 && number % 10 == 1 && number != 11)
            "Еще $number вакансия"
        else if (number % 10 in listOf(2, 3, 4) && number != 12 && number != 13 && number != 14) {
            "Еще $number вакансии"
        } else "Еще $number вакансий"
    }
    interface FragmentMainScreenInterface{
        fun clickButton()
    }
}
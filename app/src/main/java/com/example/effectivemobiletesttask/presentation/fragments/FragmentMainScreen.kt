package com.example.effectivemobiletesttask.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)

        rvBlockRecommendations = view.findViewById(R.id.rvBlockRecommendations)
        rvVacancies = view.findViewById(R.id.rvVacancies)

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

    fun updateAdapter(response: ResponseJson) { //обновить адаптер
        adapterBlockRecommendations.updateItems(response.offers)
        adapterVacancies.updateItems(response.vacancies)
    }
}
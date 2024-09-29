package com.example.effectivemobiletesttask.presentation.fragments

import RVFavoritesAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson

class FragmentFavorites : Fragment() {
    private lateinit var rvFavorites: RecyclerView
    private lateinit var tvNumberVacancies: TextView
    private val adapterFavorites by lazy { RVFavoritesAdapter() }
    private var activityInterface: FragmentFavoritesInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        rvFavorites = view.findViewById(R.id.tvFavoriteFragmentFavorites)
        tvNumberVacancies = view.findViewById(R.id.tvCountVacanciesFragmentFavorite)
        rvFavorites.layoutManager =
            LinearLayoutManager(requireContext()) // Устанавливаем стандартный layoutManager для вертикальной прокрутки
        rvFavorites.adapter = adapterFavorites
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentFavoritesInterface) { //реализуем интерфейс с активностью
            activityInterface = context
        }
        else {
            throw RuntimeException("$context activity does not implement the " +
                    "FragmentFavoritesInterface")
        }
    }

    override fun onResume() {
        super.onResume()
        activityInterface?.updateDataFromFavorites()
    }
    override fun onDetach() {
        super.onDetach()
        activityInterface = null //на всякий пожарный дабы утечек памяти не было
    }
    fun updateDataFragmentMainScreen(response: ResponseJson) { //обновить данные
        adapterFavorites.updateItems(response.vacancies)
    }
    private fun setTextFavorites(number: Int): String {//выбор склонения для
        // вывода числа вакансий
        return if (number > 0 && number % 10 == 1 && number != 11)
            "$number вакансия"
        else if (number % 10 in listOf(2, 3, 4) && number != 12 && number != 13 && number != 14) {
            "$number вакансии"
        } else "$number вакансий"
    }
    interface FragmentFavoritesInterface{
        fun updateDataFromFavorites()
    }
}


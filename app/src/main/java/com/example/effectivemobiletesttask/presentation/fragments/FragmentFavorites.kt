package com.example.effectivemobiletesttask.presentation.fragments

import RVVacanciesAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.presentation.FilterDataJson
import com.example.effectivemobiletesttask.presentation.ViewModelActivity

class FragmentFavorites : Fragment(), RVVacanciesAdapter.OnClickListenerAdapter  {
    private val viewModelActivity: ViewModelActivity by activityViewModels()
    private val filter = FilterDataJson()//фильтр данных для rv
    private lateinit var rvFavorites: RecyclerView
    private lateinit var tvNumberVacancies: TextView
    private val adapterFavorites by lazy { RVVacanciesAdapter(listener = this)  }
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
        } else {
            throw RuntimeException(
                "$context activity does not implement the " +
                        "FragmentFavoritesInterface"
            )
        }
    }

    private fun observeViewModel() {//подписываемся на обновления
        viewModelActivity.getLdJson().observe(this) {
            updateDataFragmentFavorites(it)
        }
    }

    override fun onResume() {
        super.onResume()
        observeViewModel() //подписаться и обновить данные
        //activityInterface?.updateDataFromFavorites()
    }

    override fun onDetach() {
        super.onDetach()
        activityInterface = null //на всякий пожарный дабы утечек памяти не было
    }

    private fun updateDataFragmentFavorites(response: ResponseJson) { //обновить данные
        val data = filter.forRvFavorites(response)
        val dataNumber = filter.setNumberVacancies(response)
        tvNumberVacancies.text=setTextFavorites(dataNumber)
        adapterFavorites.updateItems(data)
    }

    private fun setTextFavorites(number: Int): String {//выбор склонения для
        // вывода числа вакансий
        return if (number > 0 && number % 10 == 1 && number != 11)
            "$number вакансия"
        else if (number % 10 in listOf(2, 3, 4) && number != 12 && number != 13 && number != 14) {
            "$number вакансии"
        } else "$number вакансий"
    }

    interface FragmentFavoritesInterface {
        fun updateDataFromFavorites()
    }

    override fun onClickAdapterButtonFavorites(id: String) { //вызывается из RVVacanciesAdapter
        viewModelActivity.favoritesTrueFalse(id)
        viewModelActivity.getLdJson().value?.let {updateDataFragmentFavorites(it)}
    }
}


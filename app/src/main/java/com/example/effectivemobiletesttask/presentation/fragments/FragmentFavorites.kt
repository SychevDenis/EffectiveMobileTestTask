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
import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.FilterDataJson
import com.example.effectivemobiletesttask.presentation.ViewModelActivity

class FragmentFavorites : Fragment(), RVVacanciesAdapter.OnClickListenerAdapter  {
    private val viewModel: ViewModelActivity by activityViewModels()
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
        if (context is FragmentFavoritesInterface) { //реализован ли интерфейс с активностью
            activityInterface = context
        } else {
            throw RuntimeException(
                "$context activity does not implement the " +
                        "FragmentFavoritesInterface"
            )
        }
    }

    private fun observeViewModel() {//подписываемся на обновления
        viewModel.ldJson.observe(this) {
            updateDataFragmentFavorites(it)
        }
    }

    override fun onResume() {
        super.onResume()
        observeViewModel() //подписаться и обновить данные
    }

    override fun onDetach() {
        super.onDetach()
        activityInterface = null //на всякий пожарный дабы утечек памяти не было
    }

    private fun updateDataFragmentFavorites(response: ResponseJson) { //обновить данные
        tvNumberVacancies.text=setTextFavorites(viewModel.getNumberVacanciesInFavorites(response))
        adapterFavorites.updateData(viewModel.updateFragmentFavoritesAdapterRvVacancies(response)) //    <--------  исправить если будет время (баги)
    }

    private fun setTextFavorites(number: Int): String {//выбор склонения для
        return viewModel.choosingDeclensionTextView(number)
    }

    interface FragmentFavoritesInterface {
        fun onClickCard()
    }

    override fun onClickAdapterButtonFavorites(id: String,position: Int) { //вызывается из RVVacanciesAdapter
        viewModel.favoritesTrueFalse(id)
    }

    override fun onClickCard() {
       activityInterface?.onClickCard()
    }
}


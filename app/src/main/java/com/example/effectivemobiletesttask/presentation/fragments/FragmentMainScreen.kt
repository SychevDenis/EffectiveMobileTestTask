package com.example.effectivemobiletesttask.presentation.fragments

import RVBlockRecommendationsAdapter
import RVVacanciesAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.presentation.ViewModelActivity

class FragmentMainScreen : Fragment(), RVVacanciesAdapter.OnClickListenerAdapter {
    private val viewModel: ViewModelActivity by activityViewModels()
    private lateinit var rvBlockRecommendations: RecyclerView
    private lateinit var rvVacancies: RecyclerView
    private val adapterBlockRecommendations by lazy { RVBlockRecommendationsAdapter() }
    private val adapterVacancies by lazy { RVVacanciesAdapter(listener = this) }
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
        } else {
            throw RuntimeException(
                "$context activity does not implement the " +
                        "FragmentMainScreenInterface "
            )
        }
    }

    override fun onResume() {
        super.onResume()
        buttonMoreVacancies.setOnClickListener {//подключаем слушатель кнопки
            activityInterface?.clickButtonMoreVacancies()
        }
        observeViewModel()
    }

    override fun onDetach() {
        super.onDetach()
        activityInterface = null //на всякий пожарный дабы утечек памяти не было
    }

    private fun updateDataFragmentMainScreen(response: ResponseJson) { //обновить данные
        buttonMoreVacancies.text= setTextButtonMoreVacancies(
            viewModel.getNumberAllVacancies(response))
        adapterVacancies.updateItems(viewModel. // <--------  исправить если будет время (баги)
            updateFragmentMainScreenAdapterRvVacancies(response))
        adapterBlockRecommendations.updateItems(viewModel.
            updateFragmentMainScreenAdapterRvBlockRecommendation(response))
    }

    private fun observeViewModel() {//подписываемся на обновления
        viewModel.ldJson.observe(this) {
            updateDataFragmentMainScreen(it.copy())
        }
    }

    private fun setTextButtonMoreVacancies(number: Int): String {//выбор склонения
        return viewModel.choosingDeclensionButtonView(number)
    }

    interface FragmentMainScreenInterface {
        fun clickButtonMoreVacancies()
        fun onClickCard()
    }

    override fun onClickAdapterButtonFavorites(
        id: String,
        position: Int
    ) { //вызывается из RVVacanciesAdapter
        viewModel.favoritesTrueFalse(id)
    }

    override fun onClickCard() {
        activityInterface?.onClickCard()
    }
}

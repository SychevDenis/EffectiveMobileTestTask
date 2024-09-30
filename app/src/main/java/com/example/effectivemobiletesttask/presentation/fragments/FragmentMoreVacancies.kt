package com.example.effectivemobiletesttask.presentation.fragments

import RVVacanciesAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.presentation.FilterDataJson
import com.example.effectivemobiletesttask.presentation.ViewModelActivity

class FragmentMoreVacancies : Fragment(), RVVacanciesAdapter.OnClickListenerAdapter {
    private val viewModelActivity: ViewModelActivity by activityViewModels()
    private lateinit var editText: EditText
    private val filter = FilterDataJson()//фильтр данных для rv
    private lateinit var tvNumberVacancies: TextView
    private lateinit var compoundDrawables: Array<Drawable>
    private val adapterMoreVacancies by lazy { RVVacanciesAdapter(listener = this) }
    private lateinit var rvMoreVacancies: RecyclerView
    private lateinit var iconDrawable: Drawable
    private var activityInterface: FragmentMoreVacanciesInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more_vacancies, container, false)
        rvMoreVacancies = view.findViewById(R.id.rvVacanciesFragmentMoreVacancies)
        rvMoreVacancies.layoutManager =
            LinearLayoutManager(requireContext()) // Устанавливаем стандартный layoutManager для вертикальной прокрутки
        editText = view.findViewById(R.id.editTextFragmentMoreVacancies)
        tvNumberVacancies = view.findViewById(R.id.tvNumberVacanciesFragmentMoreVacancies)
        compoundDrawables = editText.compoundDrawables
        rvMoreVacancies.adapter = adapterMoreVacancies
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //не знаю как сделать кликабельную иконку, по этому использовал вот такой
        //обходной варивант через вычисление координат нажатия по х
        editText.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                iconDrawable = editText.compoundDrawables[0]
                if (motionEvent.x < editText.compoundDrawablePadding
                    + iconDrawable.intrinsicWidth
                ) {
                    activityInterface?.clickButtonBack()
                    return@setOnTouchListener true // Обработано, верните true
                }
            }
            false
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentMoreVacanciesInterface) { //реализуем интерфейс с активностью
            activityInterface = context
        } else {
            throw RuntimeException(
                "$context activity does not implement the " +
                        "FragmentMoreVacanciesInterface"
            )
        }
    }

    private fun observeViewModel() {//подписываемся на обновления
        viewModelActivity.getLdJson().observe(this) {
            updateDataFragmentMoreVacancies(it)
        }
    }

    override fun onResume() {
        super.onResume()
        observeViewModel() //подписаться
    }

    override fun onDetach() {
        super.onDetach()
        activityInterface = null //на всякий пожарный дабы утечек памяти не было
    }

    private fun updateDataFragmentMoreVacancies(response: ResponseJson) { //обновить данные
        val data = filter.forRvMoreVacancies(response)
        val numberVacancies = filter.setNumberVacancies(response)
        adapterMoreVacancies.updateItems(data)
        tvNumberVacancies.text = setTextMoreVacancies(numberVacancies)
    }

    private fun setTextMoreVacancies(number: Int): String {//выбор склонения для
        // вывода числа вакансий
        return if (number > 0 && number % 10 == 1 && number != 11)
            "$number вакансия"
        else if (number % 10 in listOf(2, 3, 4) && number != 12 && number != 13 && number != 14) {
            "$number вакансии"
        } else "$number вакансий"
    }

    interface FragmentMoreVacanciesInterface {
        fun clickButtonBack()
        fun updateDataFromMoreVacancies()
    }

    override fun onClickAdapterButtonFavorites(id: String) { //вызывается из RVVacanciesAdapter
        viewModelActivity.favoritesTrueFalse(id)
        viewModelActivity.getLdJson().value?.let {updateDataFragmentMoreVacancies(it)}
    }
}
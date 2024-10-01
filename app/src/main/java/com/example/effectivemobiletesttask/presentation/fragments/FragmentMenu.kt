package com.example.effectivemobiletesttask.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import com.example.effectivemobiletesttask.domain.pojo.Vacancies
import com.example.effectivemobiletesttask.presentation.ViewModelActivity

class FragmentMenu : Fragment() {
    private val viewModelActivity: ViewModelActivity by activityViewModels()

    private lateinit var ivSearchFragmentMenu: ImageView
    private lateinit var ivFavouritesFragmentMenu: ImageView
    private lateinit var ivResponsesFragmentMenu: ImageView
    private lateinit var ivMessagesFragmentMenu: ImageView
    private lateinit var ivProfileFragmentMenu: ImageView
    private lateinit var ivBubble: ImageView
    private lateinit var tvBubble: TextView
    private lateinit var llSearchFragmentMenu: LinearLayout
    private lateinit var llFavouritesFragmentMenu: LinearLayout
    private lateinit var llResponsesFragmentMenu: LinearLayout
    private lateinit var llMessagesFragmentMenu: LinearLayout
    private lateinit var llProfileFragmentMenu: LinearLayout
    private var activityInterface: FragmentMenuInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        llSearchFragmentMenu = view.findViewById(R.id.llSearchFragmentMenu)
        llFavouritesFragmentMenu = view.findViewById(R.id.llFavouritesFragmentMenu)
        llResponsesFragmentMenu = view.findViewById(R.id.llResponsesFragmentMenu)
        llMessagesFragmentMenu = view.findViewById(R.id.llMessagesFragmentMenu)
        llProfileFragmentMenu = view.findViewById(R.id.llProfileFragmentMenu)
        ivSearchFragmentMenu = view.findViewById(R.id.ivSearchFragmentMenu)
        ivFavouritesFragmentMenu = view.findViewById(R.id.ivFavouritesFragmentMenu)
        ivResponsesFragmentMenu = view.findViewById(R.id.ivResponsesFragmentMenu)
        ivMessagesFragmentMenu = view.findViewById(R.id.ivMessagesFragmentMenu)
        ivProfileFragmentMenu = view.findViewById(R.id.ivProfileFragmentMenu)
        ivBubble = view.findViewById(R.id.iv_bubble)
        tvBubble = view.findViewById(R.id.tv_bubble)
        return view
    }

    override fun onResume() {
        super.onResume()
        connectListeners()
        observeViewModel()
    }

    private fun observeViewModel() {//подписываемся на обновления vm
        viewModelActivity.ldJson.observe(this) {
            updateDataMenu(it)
        }
    }

    private fun updateDataMenu(json:ResponseJson) {//обновить фрагмент
        val vacanciesCount= searchFavorites(json.vacancies)
        if (vacanciesCount > 0) {
            ivBubble.visibility=View.VISIBLE
            tvBubble.apply {
                this.visibility=View.VISIBLE
                this.text=vacanciesCount.toString()
            }
        } else {
            ivBubble.visibility=View.GONE
            tvBubble.visibility=View.GONE
        }
    }

    private fun searchFavorites(vacancies: List<com.example.effectivemobiletesttask.domain.pojo.Vacancies>): Int {
        var countFavoritesTrue = 0
        for (item in vacancies) {
            if (item.isFavorite == true) {
                countFavoritesTrue++
            }
        }
        return countFavoritesTrue
    }

    private fun connectListeners() {//подключаем слушатели
        llSearchFragmentMenu.setOnClickListener {
            activityInterface?.clickButtonMenu(getString(R.string.fragmentMainScreen))
        }
        llFavouritesFragmentMenu.setOnClickListener {
            activityInterface?.clickButtonMenu(getString(R.string.fragmentFavorites))
        }
        llResponsesFragmentMenu.setOnClickListener {
            activityInterface?.clickButtonMenu(getString(R.string.fragmentPlug))
        }
        llMessagesFragmentMenu.setOnClickListener {
            activityInterface?.clickButtonMenu(getString(R.string.fragmentPlug))
        }
        llProfileFragmentMenu.setOnClickListener {
            activityInterface?.clickButtonMenu(getString(R.string.fragmentPlug))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentMenuInterface) { //реализуем интерфейс с активностью
            activityInterface = context
        } else {
            throw RuntimeException(
                "$context activity does not implement the " +
                        "FragmentMenuInterface"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        activityInterface = null
    }

    interface FragmentMenuInterface {
        fun clickButtonMenu(fragmentName: String)
    }
}
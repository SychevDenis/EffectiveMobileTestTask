package com.example.effectivemobiletesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.di.DaggerComponentsActivity

import com.example.effectivemobiletesttask.presentation.fragments.FragmentFavorites
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMenu
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMoreVacancies
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentMainScreen.FragmentMainScreenInterface,
    FragmentMoreVacancies.FragmentMoreVacanciesInterface,
    FragmentFavorites.FragmentFavoritesInterface, FragmentMenu.FragmentMenuInterface {

    private lateinit var controller: NavController
    private lateinit var activityExt: ExtActivityMain  //объект разширения активности

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelActivity by lazy { //получение view model через фабрику
        ViewModelProvider(this, viewModelFactory)[ViewModelActivity::class.java]
    }

    init {
        DaggerComponentsActivity.create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller = findNavController(R.id.nav_host_fragment)
        activityExt = ExtActivityMain(this)
    }

    override fun onResume() {
        super.onResume()
        activityExt.getDataAndPars(lifecycleScope, viewModelActivity)
    }

    override fun clickButtonMoreVacancies() {//вызывается из фрагмента MainScreen
        controller.navigate(R.id.action_fragment_main_screen_to_fragment_more_vacancies)
    }

    override fun onClickCard() {
        activityExt.navigate(controller, getString(R.string.fragmentPlug))
    }

    override fun clickButtonBack() { //вызывается из фрагмента MoreVacancies
        controller.navigate(R.id.action_fragment_more_vacancies_to_fragment_main_screen)
    }


    override fun clickButtonMenu(fragmentName: String) {//вызывается из фрагмента  Menu
        activityExt.navigate(controller, fragmentName)
    }

}


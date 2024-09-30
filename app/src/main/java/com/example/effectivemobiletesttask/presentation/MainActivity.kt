package com.example.effectivemobiletesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.di.DaggerComponentsActivity
import com.example.effectivemobiletesttask.presentation.fragments.FragmentFavorites
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMenu
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMoreVacancies
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentMainScreen.FragmentMainScreenInterface,
    FragmentMoreVacancies.FragmentMoreVacanciesInterface,
    FragmentFavorites.FragmentFavoritesInterface, FragmentMenu.FragmentMenuInterface {
    private lateinit var activityExt: ExtActivityMain //объект разширения активности
    private lateinit var controller: NavController

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
        // activityExt.addFragment("fragmentMoreVacancies", this)
    }

    override fun clickButtonBack() { //вызывается из фрагмента MoreVacancies
        controller.navigate(R.id.action_fragment_more_vacancies_to_fragment_main_screen)
    }

    override fun updateDataFromMoreVacancies() {//вызывается из фрагмента MoreVacancies

    }


    override fun updateDataFromFavorites() {//вызывается из фрагмента Favorites

    }

    override fun clickButtonMenu(fragmentName: String) {//вызывается из фрагмента  Menu
        if (fragmentName == getString(R.string.fragmentFavorites)) {//переход на избранное
            when (controller.currentDestination?.id) {
                R.id.fragment_main_screen -> {
                    controller.navigate(R.id.action_fragment_main_screen_to_fragment_favorites)
                }
                R.id.fragment_more_vacancies -> {
                    controller.navigate(R.id.action_fragment_more_vacancies_to_fragment_favorites)
                }
                else -> {
                }
            }
        } else if (fragmentName == getString(R.string.fragmentMainScreen)) {//переход на главный экран
            when (controller.currentDestination?.id) {
                R.id.fragment_more_vacancies -> {
                    controller.navigate(R.id.action_fragment_more_vacancies_to_fragment_main_screen)
                }
                R.id.fragment_favorites -> {
                    controller.navigate(R.id.action_fragment_favorites_to_fragment_main_screen2)
                }
                else -> {
                }
            }
        }
        // activityExt.replaceFragment(fragmentName, this)
    }

    //исправить баг 1:перейти на второй экран, перевернуть экран, стрелочка назад
}


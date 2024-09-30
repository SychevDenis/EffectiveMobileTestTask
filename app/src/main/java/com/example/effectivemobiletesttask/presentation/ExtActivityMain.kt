package com.example.effectivemobiletesttask.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.effectivemobiletesttask.R
import kotlinx.coroutines.launch

//класс расширитель для методов Активности

class ExtActivityMain(private val activity: AppCompatActivity) {

    private val url = "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDk" +
            "bfXkvgpoJprXbN85uCcD7f00r&export=download" //ссылка на файл json

    init {
        createStatusBarColor(activity)
    }

    private fun createStatusBarColor(activity: AppCompatActivity) { //сделать status bar черным
        activity.window.statusBarColor = Color.BLACK
    }

    fun navigate(controller: NavController, fragmentName: String) {//навигация по фрагментам
        when (fragmentName) {
            activity.getString(R.string.fragmentFavorites) -> {//переход на избранное
                when (controller.currentDestination?.id) {
                    R.id.fragment_main_screen -> {
                        controller.navigate(R.id.action_fragment_main_screen_to_fragment_favorites)
                    }

                    R.id.fragment_more_vacancies -> {
                        controller.navigate(R.id.action_fragment_more_vacancies_to_fragment_favorites)
                    }

                    R.id.fragment_plug -> {
                        controller.navigate(R.id.action_fragment_plug_to_fragment_favorites4)
                    }

                    else -> {
                    }
                }
            }

            activity.getString(R.string.fragmentMainScreen) -> {//переход на главный экран
                when (controller.currentDestination?.id) {
                    R.id.fragment_more_vacancies -> {
                        controller.navigate(R.id.action_fragment_more_vacancies_to_fragment_main_screen)
                    }

                    R.id.fragment_favorites -> {
                        controller.navigate(R.id.action_fragment_favorites_to_fragment_main_screen2)
                    }

                    R.id.fragment_plug -> {
                        controller.navigate(R.id.action_fragment_plug_to_fragment_main_screen2)
                    }

                    else -> {
                    }
                }
            }

            activity.getString(R.string.fragmentPlug) -> {//переход на заглушку
                when (controller.currentDestination?.id) {
                    R.id.fragment_more_vacancies -> {
                        controller.navigate(R.id.action_fragment_more_vacancies_to_fragment_plug)
                    }

                    R.id.fragment_favorites -> {
                        controller.navigate(R.id.action_fragment_favorites_to_fragment_plug)
                    }

                    R.id.fragment_main_screen -> {
                        controller.navigate(R.id.action_fragment_main_screen_to_fragment_plug)
                    }

                    else -> {
                    }
                }
            }
        }
    }

    fun getDataAndPars(
        lifecycleScope: LifecycleCoroutineScope,
        viewModelActivity: ViewModelActivity
    ) {// получение данных из сети и парсинг
        if (!viewModelActivity.checkingAvailabilityDataVm()) {
            lifecycleScope.launch {
                viewModelActivity.updateDataViewModelJson(url).collect { resultJson ->
                    resultJson?.let {
                        viewModelActivity.setLdJson(it)
                    }
                }
            }
        }
    }
}
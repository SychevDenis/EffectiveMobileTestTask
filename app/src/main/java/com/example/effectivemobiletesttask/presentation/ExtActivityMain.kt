package com.example.effectivemobiletesttask.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.presentation.fragments.FragmentFavorites
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMenu
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMoreVacancies
import kotlinx.coroutines.launch

//класс расширитель для методов Активности

class ExtActivityMain(savedInstanceState: Bundle?, activity: AppCompatActivity) {
    private val fragmentMainScreen by lazy { FragmentMainScreen() }
    private val fragmentMoreVacancies by lazy { FragmentMoreVacancies() }
    private val fragmentFavorites by lazy { FragmentFavorites() }
    private val fragmentMenu by lazy { FragmentMenu() }
    private val url = "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDk" +
            "bfXkvgpoJprXbN85uCcD7f00r&export=download"

    init {
        initActivity(savedInstanceState,activity)
    }

    private fun initActivity(
        savedInstanceState: Bundle?, activity:AppCompatActivity
    ) {//инициализация активности
        if (savedInstanceState == null) {
            addFragment(activity.getString(R.string.fragmentMainScreen), activity)
        } else {
            val nameLastBackFragment = getLastFragmentTag(activity.supportFragmentManager)
            replaceFragment(nameLastBackFragment, activity)
        }
        createStatusBarColor(activity)
    }
    private fun checkPresenceFragment(activity:AppCompatActivity,fragmentName: String):Boolean{
        //проверка, был ли ранее добавлен такой фрагмент
        return (activity.supportFragmentManager.findFragmentByTag(fragmentName) == null)
    }
    fun addFragment(fragmentName: String, activity: AppCompatActivity) {
        //добавить фрагмент в стек
        val fragment = createFragmentByTag(fragmentName, activity)
        if (checkPresenceFragment(activity,fragmentName)) {
            activity.supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, fragmentName)
                .apply {
                    if (fragmentName != activity.getString(R.string.fragmentMainScreen))
                        this.addToBackStack(fragmentName)
                }
                .commit()
        }
    }

    fun replaceFragment(fragmentName: String, activity: AppCompatActivity) {
        val fragment = createFragmentByTag(fragmentName, activity)
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragmentName)
            .commit()
    }

    private fun createFragmentByTag(fragmentName: String, activity: AppCompatActivity): Fragment {
        // получить фрагмент по тегу
        return when (fragmentName) {
            activity.getString(R.string.fragmentMainScreen) -> fragmentMainScreen
            activity.getString(R.string.fragmentMoreVacancies) -> fragmentMoreVacancies
            activity.getString(R.string.fragmentFavorites) -> fragmentFavorites
            activity.getString(R.string.fragmentMenu) -> fragmentMenu
            activity.getString(R.string.empty) -> fragmentMainScreen
            else -> throw IllegalArgumentException(activity.getString(R.string.unknown_format)
                    + ": $fragmentName")
        }
    }

    private fun getLastFragmentTag(supportFragmentManager: FragmentManager): String {
        //получение имени последнего фрагмента
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount > 0) {
            val lastNameFragment =
                supportFragmentManager.getBackStackEntryAt(backStackEntryCount - 1).name
            return lastNameFragment ?: run {
                return "null"
            }
        } else {
            return "empty"
        }
    }

    private fun createStatusBarColor(activity: AppCompatActivity) { //сделать status bar черным
        activity.window.statusBarColor = Color.BLACK
    }

    fun getDataAndPars(lifecycleScope:LifecycleCoroutineScope,
                       viewModelActivity:ViewModelActivity){// получение данных из сети и парсинг
        lifecycleScope.launch {
            viewModelActivity.updateDataViewModelJson(url).collect { resultJson ->
                resultJson?.let {
                    viewModelActivity.setLdJson(it)
                }
            }
        }
    }
}
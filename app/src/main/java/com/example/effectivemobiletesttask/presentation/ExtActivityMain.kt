package com.example.effectivemobiletesttask.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch

//класс расширитель для методов Активности

class ExtActivityMain(activity: AppCompatActivity) {
//    private val fragmentMainScreen by lazy { FragmentMainScreen() }
//    private val fragmentMoreVacancies by lazy { FragmentMoreVacancies() }
//    private val fragmentFavorites by lazy { FragmentFavorites() }
//    private val fragmentMenu by lazy { FragmentMenu() }
    private val url = "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDk" +
            "bfXkvgpoJprXbN85uCcD7f00r&export=download"

    init {
        createStatusBarColor(activity)
    }

 //   private fun getBackStackNames(activity: AppCompatActivity) {
//        val fragmentManager = activity.supportFragmentManager
//        val backStackEntryCount = fragmentManager.backStackEntryCount
//        val names = mutableListOf<String?>()
//        for (i in 0 until backStackEntryCount) {
//            val backStackEntry = fragmentManager.getBackStackEntryAt(i)
//            names.add(backStackEntry.name)
//        }
//        println(names)
//    }

//    private fun initActivity(
//        savedInstanceState: Bundle?, activity: AppCompatActivity
//    ) {//инициализация активности
////        if (savedInstanceState == null) {
////            addFragment(activity.getString(R.string.fragmentMainScreen), activity)
////        } else {
////            val nameLastBackFragment = getLastFragmentTag(activity.supportFragmentManager)
////            replaceFragment(nameLastBackFragment, activity)
////        }
//
//    }
//
//    private fun checkPresenceFragment(activity: AppCompatActivity, fragmentName: String): Boolean {
//        //проверка, был ли ранее добавлен такой фрагмент
//        return (activity.supportFragmentManager.findFragmentByTag(fragmentName) == null)
//    }
//
//    fun removeFragment(activity: AppCompatActivity, fragmentName: String) {
//        //удалить фрагмент по имени
//        val fragment = activity.supportFragmentManager.findFragmentByTag(fragmentName)
//        if (fragment != null) {
//            activity.supportFragmentManager.beginTransaction()
//                .remove(fragment)
//                .commit()
//        }
//    }
//
//    fun addFragment(fragmentName: String, activity: AppCompatActivity) {
//        //добавить фрагмент в стек
//
//        val fragment = createFragmentByTag(fragmentName, activity)
//        activity.supportFragmentManager.beginTransaction()
//            .add(R.id.nav_host_fragment, fragment, fragmentName)
//            .apply {
//                if (fragmentName != activity.getString(R.string.fragmentMainScreen))
//                    this.addToBackStack(fragmentName)
//            }
//            .commit()
//        println("add")
//        getBackStackNames(activity)
//    }
//
//    fun replaceFragment(fragmentName: String, activity: AppCompatActivity) {
//        val fragment = createFragmentByTag(fragmentName, activity)
//
//        activity.supportFragmentManager.beginTransaction()
//            .replace(R.id.nav_host_fragment, fragment, fragmentName)
//            .commit()
//        println("replace")
//        getBackStackNames(activity)
//    }
//
//    private fun createFragmentByTag(fragmentName: String, activity: AppCompatActivity): Fragment {
//        // получить фрагмент по тегу
//        return when (fragmentName) {
//            activity.getString(R.string.fragmentMainScreen) -> fragmentMainScreen
//            activity.getString(R.string.fragmentMoreVacancies) -> fragmentMoreVacancies
//            activity.getString(R.string.fragmentFavorites) -> fragmentFavorites
//            activity.getString(R.string.fragmentMenu) -> fragmentMenu
//            activity.getString(R.string.empty) -> fragmentMainScreen
//            else -> throw IllegalArgumentException(
//                activity.getString(R.string.unknown_format)
//                        + ": $fragmentName"
//            )
//        }
//    }
//
//    private fun getLastFragmentTag(supportFragmentManager: FragmentManager): String {
//        //получение имени последнего фрагмента
//        val backStackEntryCount = supportFragmentManager.backStackEntryCount
//        if (backStackEntryCount > 0) {
//            val lastNameFragment =
//                supportFragmentManager.getBackStackEntryAt(backStackEntryCount - 1).name
//            return lastNameFragment ?: run {
//                return "null"
//            }
//        } else {
//            return "empty"
//        }
//    }

    private fun createStatusBarColor(activity: AppCompatActivity) { //сделать status bar черным
        activity.window.statusBarColor = Color.BLACK
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
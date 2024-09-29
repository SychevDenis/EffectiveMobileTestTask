package com.example.effectivemobiletesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.di.DaggerComponentsActivity
import com.example.effectivemobiletesttask.presentation.fragments.FragmentFavorites
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMenu
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMoreVacancies
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentMainScreen.FragmentMainScreenInterface,
    FragmentMoreVacancies.FragmentMoreVacanciesInterface,
    FragmentFavorites.FragmentFavoritesInterface, FragmentMenu.FragmentMenuInterface {

    private lateinit var activityExt: ExtActivityMain //объект разширения активности

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
        activityExt = ExtActivityMain(savedInstanceState, this)

    }

    override fun onResume() {
        super.onResume()
        activityExt.getDataAndPars(lifecycleScope,viewModelActivity)
    }

//    private fun getLastFragmentTag(): String { //получение имени последнего фрагмента
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
//    private fun getBackStackNames() {
//        val fragmentManager = supportFragmentManager
//        val backStackEntryCount = fragmentManager.backStackEntryCount
//        val names = mutableListOf<String?>()
//        for (i in 0 until backStackEntryCount) {
//            val backStackEntry = fragmentManager.getBackStackEntryAt(i)
//            names.add(backStackEntry.name)
//        }
//        println(names)
//    }
    override fun clickButtonMoreVacancies() {//вызывается из фрагмента MainScreen
        activityExt.addFragment("fragmentMoreVacancies", this)
    }

    override fun clickButtonBack() { //вызывается из фрагмента MoreVacancies
        onBackPressed()
    }

    override fun updateDataFromMoreVacancies() {//вызывается из фрагмента MoreVacancies

    }

    override fun updateDataFromMainScreen() {//вызывается из фрагмента MainScreen

    }

    override fun updateDataFromFavorites() {//вызывается из фрагмента Favorites

    }

    override fun clickButtonMenu(fragmentName: String) {//вызывается из фрагмента  Favorites
        activityExt.addFragment(fragmentName, this)
    }

    //исправить баг 1:перейти на второй экран, перевернуть экран, стрелочка назад
    //исправить вылет при нажатии 2 раза на избранное
}


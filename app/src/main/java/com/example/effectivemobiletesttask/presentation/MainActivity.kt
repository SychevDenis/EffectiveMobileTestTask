package com.example.effectivemobiletesttask.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.di.DaggerComponentsActivity
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMoreVacancies
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentMainScreen.FragmentMainScreenInterface,
    FragmentMoreVacancies.FragmentMoreVacanciesInterface {
    private val url =
        "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download"

    private val fragmentMainScreen by lazy { FragmentMainScreen() }
    private val fragmentMoreVacancies by lazy { FragmentMoreVacancies() }

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
        createStatusBarColor()
        if (savedInstanceState == null) {
            addFragment("fragmentMainScreen")
        } else {
            val nameLastBackFragment = getLastFragmentTag()
            replaceFragment(nameLastBackFragment)
        }

    }

    override fun updateDataFromMainScreen() {
        lifecycleScope.launchWhenCreated {
            viewModelActivity.fetchData(url).collect { resultJson ->
                resultJson?.let {
                    //  viewModelActivity.setLdJson(it)
                    fragmentMainScreen.updateDataFragmentMainScreen(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getBackStackNames()
    }

    private fun getLastFragmentTag(): String { //получение имени последнего фрагмента
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

    private fun createStatusBarColor() { //сделать status bar черным
        window.statusBarColor = Color.BLACK
    }

    private fun replaceFragment(fragmentName: String) {
        val fragment = createFragmentByTag(fragmentName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragmentName)
            .commit()
    }

    private fun addFragment(fragmentName: String) {
        //добавить фрагмент в стек
        val fragment = createFragmentByTag(fragmentName)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, fragmentName)
            .apply {
                if (fragmentName != "fragmentMainScreen")
                    this.addToBackStack(fragmentName)
            }
            .commit()
    }

    private fun createFragmentByTag(fragmentName: String): Fragment {
        // получить фрагмент по тегу
        when (fragmentName) {
            "fragmentMainScreen" -> return fragmentMainScreen
            "fragmentMoreVacancies" -> return fragmentMoreVacancies
            "empty" -> return fragmentMainScreen
            else -> throw IllegalArgumentException("Неизвестный фрагмент: $fragmentName")
        }
    }

    private fun getBackStackNames() {
        val fragmentManager = supportFragmentManager
        val backStackEntryCount = fragmentManager.backStackEntryCount
        val names = mutableListOf<String?>()
        for (i in 0 until backStackEntryCount) {
            val backStackEntry = fragmentManager.getBackStackEntryAt(i)
            names.add(backStackEntry.name)
        }
        println(names)
    }

    override fun clickButtonMoreVacancies() {//вызывается из фрагмента MainScreen
        addFragment("fragmentMoreVacancies")
    }

    override fun clickButtonBack() {
        getBackStackNames()
    }

    override fun updateDataFromMoreVacancies() {
        lifecycleScope.launchWhenCreated {
            viewModelActivity.fetchData(url).collect { resultJson ->
                resultJson?.let {
                    //  viewModelActivity.setLdJson(it)
                    fragmentMoreVacancies.updateDataFragmentMainScreen(it)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    //исправить баг 1:перейти на второй экран, перевернуть экран, стрелочка назад
}


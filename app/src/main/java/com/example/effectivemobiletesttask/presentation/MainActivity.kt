package com.example.effectivemobiletesttask.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.di.DaggerComponentsActivity
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMoreVacancies
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentMainScreen.FragmentMainScreenInterface {
    private val url =
        "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download"

    private lateinit var fragmentMainScreen: FragmentMainScreen
    private lateinit var fragmentMoreVacancies: FragmentMoreVacancies

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
        fragmentMainScreen = FragmentMainScreen()
        fragmentMoreVacancies = FragmentMoreVacancies()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragmentMainScreen, "fragmentMainScreen").commit()

        lifecycleScope.launchWhenCreated {
            viewModelActivity.fetchData(url).collect { resultJson ->
                resultJson?.let {
                    viewModelActivity.setLdJson(it)
                    fragmentMainScreen.updateDataFragmentMainScreen(it)
                }
            }
        }
    }

    private fun createStatusBarColor() { //сделать status bar черным
        window.statusBarColor = Color.BLACK
    }

    override fun clickButton() {//вызывается из фрагмента
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragmentMoreVacancies, "fragmentMoreVacancies")
            .addToBackStack("fragmentMoreVacancies")
            .commit()
    }
}


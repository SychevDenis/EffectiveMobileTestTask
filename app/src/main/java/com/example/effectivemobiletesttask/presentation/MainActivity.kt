package com.example.effectivemobiletesttask.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.di.DaggerComponentsActivity
import com.example.effectivemobiletesttask.presentation.RecyclerViewBlockRecommendations.RVBlockRecommendationsAdapter
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val url =
        "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download"

    private lateinit var fragmentMainScreen: FragmentMainScreen

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
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragmentMainScreen, "fragmentMainScreen").commit()
        lifecycleScope.launchWhenCreated {
            viewModelActivity.fetchData(url).collect { resultJson ->
                resultJson?.let {
                   // viewModelActivity.setLdJson(it)
                    fragmentMainScreen.updateAdapter(it)
                }
            }
        }
    }

    private fun createStatusBarColor() { //сделать status bar черным
        window.statusBarColor = Color.BLACK
    }
}


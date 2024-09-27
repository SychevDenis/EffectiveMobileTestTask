package com.example.effectivemobiletesttask.presentation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.di.DaggerComponentsActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModelActivity by lazy { //получение view model через фабрику
        ViewModelProvider(this, viewModelFactory)[ViewModelActivity::class.java]
    }

    init {
        DaggerComponentsActivity.create().inject(this)
    }

    private val url =
        "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenCreated {
            viewModelActivity.fetchData(url).collect { resultJson ->
                print(resultJson)
            }
        }
    }

    fun createStatusBarColor() {
        window.statusBarColor = Color.BLACK
    }
}


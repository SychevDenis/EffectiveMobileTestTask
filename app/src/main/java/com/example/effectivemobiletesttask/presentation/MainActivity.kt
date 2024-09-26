package com.example.effectivemobiletesttask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.effectivemobiletesttask.R

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentMainScreen: FragmentMainScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentMainScreen = FragmentMainScreen()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentMainScreen)
            .commit()
    }
}
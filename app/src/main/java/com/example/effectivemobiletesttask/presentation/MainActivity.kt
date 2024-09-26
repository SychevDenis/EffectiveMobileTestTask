package com.example.effectivemobiletesttask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMainScreen
import com.example.effectivemobiletesttask.presentation.fragments.FragmentMenu

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentMainScreen: FragmentMainScreen
    private lateinit var fragmentMenu: FragmentMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentMainScreen = FragmentMainScreen()
        fragmentMenu = FragmentMenu()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentMainScreen)
            .add(R.id.fragment_container_menu, fragmentMenu)
            .commit()
    }
}
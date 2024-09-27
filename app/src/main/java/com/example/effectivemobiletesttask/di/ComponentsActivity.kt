package com.example.effectivemobiletesttask.di

import com.example.effectivemobiletesttask.di.modules.ModuleRepositoryImplActivity
import com.example.effectivemobiletesttask.di.modules.ModuleViewModel
import com.example.effectivemobiletesttask.presentation.MainActivity
import dagger.Component

@Component(
    modules = [
        ModuleRepositoryImplActivity::class,
        ModuleViewModel::class
    ]
)

interface ComponentsActivity {
    fun inject(activity: MainActivity)
}
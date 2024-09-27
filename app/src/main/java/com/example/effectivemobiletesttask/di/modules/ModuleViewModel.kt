package com.example.effectivemobiletesttask.di.modules

import androidx.lifecycle.ViewModel
import com.example.effectivemobiletesttask.presentation.ViewModelActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ModuleViewModel {
    @IntoMap
    @StringKey("ViewModelActivity")
    @Binds
    fun bindViewModelActivity(viewModelActivity: ViewModelActivity):ViewModel
 }
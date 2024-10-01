package com.example.effectivemobiletesttask.di.modules

import com.example.effectivemobiletesttask.data.RepositoryImpl
import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.ChoosingDeclension
import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.FilterDataJson
import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.RequestJson
import com.example.effectivemobiletesttask.domain.Repository
import dagger.Module
import dagger.Provides

@Module
class ModuleRepositoryImplActivity{
    @Provides
    fun provideRepositoryImpl(
        getJson: RequestJson,
        choosingDeclensionText:ChoosingDeclension,
        filter: FilterDataJson,
    ): Repository {
        return RepositoryImpl(getJson,choosingDeclensionText,filter)
    }
}
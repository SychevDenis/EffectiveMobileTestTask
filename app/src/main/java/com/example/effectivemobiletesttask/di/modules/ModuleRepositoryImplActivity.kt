package com.example.effectivemobiletesttask.di.modules

import com.example.effectivemobiletesttask.data.RepositoryImplActivity
import com.example.effectivemobiletesttask.data.metodsRepositoruImplActivity.RequestJson
import com.example.effectivemobiletesttask.domain.RepositoryActivity
import dagger.Module
import dagger.Provides

@Module
class ModuleRepositoryImplActivity{
    @Provides
    fun provideRepositoryImpl(
        getJson: RequestJson
    ): RepositoryActivity {
        return RepositoryImplActivity(
            getJson
        )
    }
}
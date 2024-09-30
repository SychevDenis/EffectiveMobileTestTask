package com.example.effectivemobiletesttask.domain.use_cases

import com.example.effectivemobiletesttask.domain.Repository
import javax.inject.Inject

class ChoosingDeclensionTextUseCase @Inject constructor(private val repositoryActivity: Repository) {
    fun run(number: Int): String {
        return repositoryActivity.choosingDeclensionText(number)
    }
}
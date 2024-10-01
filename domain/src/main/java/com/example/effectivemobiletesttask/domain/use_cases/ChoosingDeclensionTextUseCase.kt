package com.example.effectivemobiletesttask.domain.use_cases

import com.example.effectivemobiletesttask.domain.Repository
import javax.inject.Inject

class ChoosingDeclensionTextUseCase @Inject constructor(private val repositoryActivity: Repository) {
    fun buttonView(number: Int): String {
        return repositoryActivity.choosingDeclensionButtonView(number)
    }
    fun textView(number: Int): String {
        return repositoryActivity.choosingDeclensionTextView(number)
    }
}
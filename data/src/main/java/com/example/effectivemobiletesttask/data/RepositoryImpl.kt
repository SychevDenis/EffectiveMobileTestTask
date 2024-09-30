package com.example.effectivemobiletesttask.data

import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.ChoosingDeclensionText
import com.example.effectivemobiletesttask.data.metodsRepositoruImpl.RequestJson
import com.example.effectivemobiletesttask.domain.Repository

class RepositoryImpl(
    private val requestJson: RequestJson,
    private val choosingDeclensionText: ChoosingDeclensionText
) : Repository {
    override fun requestJson(url: String): String {
        return requestJson.runRequest(url)
    }
    override fun choosingDeclensionText(number: Int): String {
        return choosingDeclensionText.run(number)
    }

}




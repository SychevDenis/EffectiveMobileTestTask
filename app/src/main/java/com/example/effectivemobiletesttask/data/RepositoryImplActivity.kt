package com.example.effectivemobiletesttask.data

import com.example.effectivemobiletesttask.data.metodsRepositoruImplActivity.RequestJson
import com.example.effectivemobiletesttask.domain.RepositoryActivity

class RepositoryImplActivity(
    private val requestJson: RequestJson
) : RepositoryActivity {
    override fun requestJson(url: String): String {
        return requestJson.runRequest(url)
    }

}




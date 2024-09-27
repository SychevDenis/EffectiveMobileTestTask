package com.example.effectivemobiletesttask.domain

interface RepositoryActivity {
    fun requestJson(url: String): String
}
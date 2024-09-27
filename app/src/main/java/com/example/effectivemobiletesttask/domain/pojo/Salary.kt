package com.example.effectivemobiletesttask.domain.pojo
import com.google.gson.annotations.SerializedName

data class Salary(
    @SerializedName("full") var full: String? = null
)
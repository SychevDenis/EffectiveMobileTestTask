package com.example.effectivemobiletesttask.domain.pojo

import com.google.gson.annotations.SerializedName

data class ResponseJson(
    @SerializedName("offers") var offers: ArrayList<Offers> = arrayListOf(),
    @SerializedName("vacancies") var vacancies: ArrayList<Vacancies> = arrayListOf()
)
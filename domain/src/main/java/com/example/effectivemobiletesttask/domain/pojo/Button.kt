package com.example.effectivemobiletesttask.domain.pojo

import com.google.gson.annotations.SerializedName

data class Button(
    @SerializedName("text") var text: String? = null
)
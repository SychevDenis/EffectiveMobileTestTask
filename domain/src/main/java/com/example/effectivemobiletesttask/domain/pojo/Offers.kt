package com.example.effectivemobiletesttask.domain.pojo

import com.google.gson.annotations.SerializedName

data class Offers(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("button") var button: Button? = null,
    @SerializedName("link") var link: String? = null,
)
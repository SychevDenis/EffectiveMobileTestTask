package com.example.effectivemobiletesttask.domain.pojo

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("town") var town: String? = null,
    @SerializedName("street") var street: String? = null,
    @SerializedName("house") var house: String? = null
)
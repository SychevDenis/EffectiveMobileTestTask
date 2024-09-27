package com.example.effectivemobiletesttask.domain.pojo
import com.google.gson.annotations.SerializedName

data class Vacancies(
    @SerializedName("id") var id: String? = null,
    @SerializedName("lookingNumber") var lookingNumber: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("address") var address: Address? = Address(),
    @SerializedName("company") var company: String? = null,
    @SerializedName("experience") var experience: Experience? = Experience(),
    @SerializedName("publishedDate") var publishedDate: String? = null,
    @SerializedName("isFavorite") var isFavorite: Boolean? = null,
    @SerializedName("salary") var salary: Salary? = Salary(),
    @SerializedName("schedules") var schedules: ArrayList<String> = arrayListOf(),
    @SerializedName("appliedNumber") var appliedNumber: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("responsibilities") var responsibilities: String? = null,
    @SerializedName("questions") var questions: ArrayList<String> = arrayListOf()
)
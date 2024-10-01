package com.example.effectivemobiletesttask.domain.use_cases

import com.example.effectivemobiletesttask.domain.Repository
import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.domain.pojo.ResponseJson
import javax.inject.Inject

class UpdateAdapterRvBlockRecommendationUseCase
@Inject constructor(private val repositoryActivity: Repository) {
    fun fragmentMainScreen(response: ResponseJson):  List<Offers> {
        return repositoryActivity.updateFragmentMainScreenAdapterRvBlockRecommendation(response)
    }
}
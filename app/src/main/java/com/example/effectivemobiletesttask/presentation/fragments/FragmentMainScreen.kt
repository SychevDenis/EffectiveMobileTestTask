package com.example.effectivemobiletesttask.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.Offers
import com.example.effectivemobiletesttask.presentation.RecyclerViewBlockRecommendations.RVBlockRecommendationsAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMainScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMainScreen : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RVBlockRecommendationsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)
        recyclerView = view.findViewById(R.id.rvBlockRecommendations)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = RVBlockRecommendationsAdapter()
        recyclerView.adapter = adapter
        return view
    }
    fun updateAdapter(listOffers:List<Offers>){ //обновить адаптер
        adapter.updateItems(listOffers)
    }
}
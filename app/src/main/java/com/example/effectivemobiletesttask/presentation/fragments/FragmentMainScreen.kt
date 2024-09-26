package com.example.effectivemobiletesttask.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.effectivemobiletesttask.R

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMainScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMainScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }
}
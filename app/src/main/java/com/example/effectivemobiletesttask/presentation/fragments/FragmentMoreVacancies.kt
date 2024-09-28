package com.example.effectivemobiletesttask.presentation.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.effectivemobiletesttask.R


class FragmentMoreVacancies : Fragment() {
    private lateinit var editText: EditText
    private lateinit var compoundDrawables: Array<Drawable>
    private lateinit var iconDrawable: Drawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more_vacancies, container, false)
        editText = view.findViewById(R.id.editTextFragmentMoreVacancies)
        compoundDrawables = editText.compoundDrawables

        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                iconDrawable = editText.compoundDrawables[0]
                if (motionEvent.x < editText.compoundDrawablePadding
                    + iconDrawable.intrinsicWidth) {
                    requireActivity().onBackPressed()
                    return@setOnTouchListener true // Обработано, верните true
                }
            }
            false // Необработано, верните false
        }
    }
}
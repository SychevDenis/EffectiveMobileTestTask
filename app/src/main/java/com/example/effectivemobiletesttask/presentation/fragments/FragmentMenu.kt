package com.example.effectivemobiletesttask.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.presentation.ViewModelActivity

class FragmentMenu : Fragment() {
    private val viewModelActivity: ViewModelActivity by activityViewModels()
    private lateinit var llSearchFragmentMenu: LinearLayout
    private lateinit var llFavouritesFragmentMenu: LinearLayout
    private lateinit var llResponsesFragmentMenu: LinearLayout
    private lateinit var llMessagesFragmentMenu: LinearLayout
    private lateinit var llProfileFragmentMenu: LinearLayout
    private var activityInterface: FragmentMenuInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        llSearchFragmentMenu=view.findViewById(R.id.llSearchFragmentMenu)
        llFavouritesFragmentMenu=view.findViewById(R.id.llFavouritesFragmentMenu)
        llResponsesFragmentMenu=view.findViewById(R.id.llResponsesFragmentMenu)
        llMessagesFragmentMenu=view.findViewById(R.id.llMessagesFragmentMenu)
        llProfileFragmentMenu=view.findViewById(R.id.llProfileFragmentMenu)
        return view
    }

    override fun onResume() {
        super.onResume()
        connectListeners()
    }
    private fun observeViewModel(){//подписываемся на обновления
//        viewModelActivity.getLdJson().observe(this){
//            updateDataMenu(it)
//        }
    }
    private fun connectListeners(){//подключаем слушатели
        llSearchFragmentMenu.setOnClickListener{

        }
        llFavouritesFragmentMenu.setOnClickListener{
            println("press favorites")
            activityInterface?.clickButtonMenu(getString(R.string.fragmentFavorites))
        }
        llResponsesFragmentMenu.setOnClickListener{

        }
        llMessagesFragmentMenu.setOnClickListener{

        }
        llProfileFragmentMenu.setOnClickListener{

        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentMenuInterface) { //реализуем интерфейс с активностью
            activityInterface = context
        }
        else {
            throw RuntimeException("$context activity does not implement the " +
                    "FragmentMenuInterface")
        }
    }
    override fun onDetach() {
        super.onDetach()
        activityInterface = null
    }
    interface FragmentMenuInterface{
        fun clickButtonMenu(fragmentName:String)
    }
}
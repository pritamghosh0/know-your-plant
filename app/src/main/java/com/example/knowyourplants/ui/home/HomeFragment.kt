package com.example.knowyourplants.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.knowyourplants.R
import kotlin.random.Random

class HomeFragment : Fragment() {
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        val tvHome = view.findViewById<TextView>(R.id.tv_home)
        tvHome?.setOnClickListener {
            val plantIdAction = HomeFragmentDirections.actionHomeToDetails()
            plantIdAction.plantId = Random.nextInt(1000)
            navController.navigate(plantIdAction)
        }
    }
}
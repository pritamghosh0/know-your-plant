package com.example.knowyourplants.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.knowyourplants.R
import com.example.knowyourplants.data.remote.models.PlantListResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val TAG: String = "HomeFragment"
    private lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModels()

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
        viewModel.getPlants()
        lifecycleScope.launch {
            viewModel.plants.collect { response ->
                if(response!=null)
                    showList(response)
            }
        }
    }

    private fun showList(plantList: PlantListResponse){
        //TODO
        Log.d(TAG, plantList.data.toString())
    }
}
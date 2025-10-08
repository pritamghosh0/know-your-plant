package com.example.knowyourplants.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowyourplants.R
import com.example.knowyourplants.data.remote.ApiResponse
import com.example.knowyourplants.data.remote.models.plant.PlantListItem
import com.example.knowyourplants.ui.home.recyclerview.PlantsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val TAG: String = "HomeFragment"
    private lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var plantsListAdapter: PlantsListAdapter
    private var lastItemCount = 0 // track how many items we had before

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
//        val tvHome = view.findViewById<TextView>(R.id.tv_home)
//        tvHome?.setOnClickListener {
//            val plantIdAction = HomeFragmentDirections.actionHomeToDetails()
//            plantIdAction.plantId = Random.nextInt(1000)
//            navController.navigate(plantIdAction)
//        }
        progressBar = view.findViewById(R.id.progressBar)
        setupPlantRV(view)
        viewModel.getPlants()
        lifecycleScope.launch {
            viewModel.plants.collect { plantsResponse ->
                when(plantsResponse){
                    is ApiResponse.Success -> showList(plantsResponse.data)
                    is ApiResponse.Error -> showAlert(plantsResponse.message)
                    ApiResponse.Loading -> showLoader()
                }
            }
        }
    }

    private fun setupPlantRV(root: View) {
        plantsListAdapter = PlantsListAdapter()
        val rv: RecyclerView = root.findViewById(R.id.rv_plantsList)
        rv.apply {
            adapter = plantsListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                if ((visibleItemCount + pastVisibleItem) >= totalItemCount) { // near bottom
                    viewModel.getPlants()
                }
            }
        })
    }

    private fun showAlert(error: String){
        //TODO
        Log.d(TAG, "Error: $error")
    }
    private fun hideLoader(){
        progressBar.visibility = View.GONE
    }
    private fun showLoader(){
        progressBar.visibility = View.VISIBLE
    }
    private fun showList(plantList: List<PlantListItem>){
        requireActivity().runOnUiThread {
            hideLoader()
            if (lastItemCount == 0) {
                // First load
                plantsListAdapter.setData(plantList)
            } else if (plantList.size > lastItemCount) {
                // Append detected
                val newItems = plantList.subList(lastItemCount, plantList.size)
                plantsListAdapter.addData(newItems)
            } else {
                // Probably refresh or replace
                plantsListAdapter.setData(plantList)
            }

            lastItemCount = plantList.size
        }
    }
}
package com.example.knowyourplants.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowyourplants.data.remote.ApiResponse
import com.example.knowyourplants.data.remote.models.plant.PlantListItem
import com.example.knowyourplants.repository.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {
    private var pageNo = 1
    private val _plants = MutableStateFlow<ApiResponse<List<PlantListItem>>>(ApiResponse.Loading)
    val plants: StateFlow<ApiResponse<List<PlantListItem>>> = _plants
    private val persistedList = mutableListOf<PlantListItem>()

    fun getPlants() = viewModelScope.launch(Dispatchers.IO) {
        plantsRepository.getPlants(pageNo).collect { plantsResponse->
            when(plantsResponse){
                is ApiResponse.Success -> {
                    val newResponse = plantsResponse.data
                    pageNo = newResponse.currentPage?.plus(1) ?: pageNo
                    val currentList = persistedList
                    if (currentList.isNotEmpty() && newResponse.data.isNotEmpty()) {
                        val lastId = currentList.last().id
                        val firstNewId = newResponse.data.first().id
                        if (firstNewId == lastId?.plus(1)) {
                            val updatedList = currentList + newResponse.data
                            persistedList.addAll(newResponse.data)
                            _plants.value = ApiResponse.Success(updatedList)
                        } else {
                            // IDs not sequential, maybe refresh list
                            _plants.value = ApiResponse.Success(persistedList)
                        }
                    } else {
                        // initial load
                        persistedList.addAll(newResponse.data)
                        _plants.value = ApiResponse.Success(newResponse.data)
                    }
                }
                is ApiResponse.Loading -> _plants.value = plantsResponse
                is ApiResponse.Error -> {
                    _plants.value = plantsResponse
                    // Dummy response when api limit exceeded
                    /*
                    var start = 1
                    val currentList = persistedList
                    if(currentList.isNotEmpty())
                        start = currentList.last().id?.plus(1) ?: start
                    val dummyPlants = (start..start+30).map { id ->
                        PlantListItem(
                            id = id,
                            commonName = "Plant $id",
                            scientificName = arrayListOf("Scientificus plantus $id"),
                            otherName = arrayListOf("OtherName $id"),
                            family = "Family $id",
                            subspecies = null,
                            cultivar = null,
                            variety = null,
                            speciesEpithet = "Epithet $id",
                            genus = "Genus $id",
                            defaultImage = null
                        )
                    }
                    persistedList.addAll(dummyPlants)
                    _plants.value = ApiResponse.Success(persistedList)
                     */
                }
            }
        }
    }
}
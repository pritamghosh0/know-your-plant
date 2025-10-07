package com.example.knowyourplants.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowyourplants.data.remote.models.PlantListResponse
import com.example.knowyourplants.repository.PlantsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModel() {
    private val _plants = MutableStateFlow<PlantListResponse?>(null)
    val plants: StateFlow<PlantListResponse?> = _plants

    fun getPlants() = viewModelScope.launch {
        plantsRepository.getPlants(1).collect { plantsResponse->
            _plants.value = plantsResponse
        }
    }
}
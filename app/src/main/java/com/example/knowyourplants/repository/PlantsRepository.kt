package com.example.knowyourplants.repository

import com.example.knowyourplants.data.remote.ApiResponse
import com.example.knowyourplants.data.remote.models.PlantDetails
import com.example.knowyourplants.data.remote.models.PlantListResponse
import kotlinx.coroutines.flow.Flow

interface PlantsRepository {
    fun getPlants(page: Int) : Flow<ApiResponse<PlantListResponse>>
    fun getPlantDetails(plantId: String): Flow<ApiResponse<PlantDetails>>
}
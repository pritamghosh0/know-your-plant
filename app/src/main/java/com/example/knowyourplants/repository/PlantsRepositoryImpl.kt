package com.example.knowyourplants.repository

import com.example.knowyourplants.data.remote.PlantsApiService
import com.example.knowyourplants.data.remote.models.PlantDetails
import com.example.knowyourplants.data.remote.models.PlantListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlantsRepositoryImpl @Inject constructor(private val plantsApiService: PlantsApiService) : PlantsRepository {
    override fun getPlants(page: Int): Flow<PlantListResponse?> = flow {
        emit(null)
        emit(plantsApiService.getPlants(page = page))
    }

    override fun getPlantDetails(plantId: String): Flow<PlantDetails?> = flow{
        emit(null)
        emit(plantsApiService.getSpeciesDetails(plantId = plantId))
    }
}
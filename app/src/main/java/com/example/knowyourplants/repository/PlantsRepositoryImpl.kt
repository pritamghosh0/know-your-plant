package com.example.knowyourplants.repository

import com.example.knowyourplants.data.remote.ApiResponse
import com.example.knowyourplants.data.remote.PlantsApiService
import com.example.knowyourplants.data.remote.models.PlantDetails
import com.example.knowyourplants.data.remote.models.PlantListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlantsRepositoryImpl @Inject constructor(private val plantsApiService: PlantsApiService) : PlantsRepository {
    override fun getPlants(page: Int): Flow<ApiResponse<PlantListResponse>> = flow {
        emit(ApiResponse.Loading)
        val response = plantsApiService.getPlants(page = page)
        if(response.isSuccessful){
            response.body()?.let {
                emit(ApiResponse.Success(it))
            } ?: emit(ApiResponse.Error("No data available"))
        } else
            emit(ApiResponse.Error(response.message()))
    }

    override fun getPlantDetails(plantId: String): Flow<ApiResponse<PlantDetails>> = flow {
        emit(ApiResponse.Loading)
        val response = plantsApiService.getSpeciesDetails(plantId = plantId)
        if(response.isSuccessful){
            response.body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.Error("No data available")
        } else
            ApiResponse.Error(response.message())
    }
}
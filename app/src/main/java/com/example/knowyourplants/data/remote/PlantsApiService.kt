package com.example.knowyourplants.data.remote

import com.example.knowyourplants.Constants
import com.example.knowyourplants.data.remote.models.plant.PlantDetails
import com.example.knowyourplants.data.remote.models.plant.PlantListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantsApiService {
    @GET("/api/v2/species-list")
    suspend fun getPlants(
        @Query("key") key: String = Constants.KEY,
        @Query("page") page: Int
    ): Response<PlantListResponse>

    @GET("/api/v2/species/details/{plant_id}")
    suspend fun getSpeciesDetails(
        @Path("plant_id") plantId: String,
        @Query("key") key: String = Constants.KEY
    ): Response<PlantDetails>
}
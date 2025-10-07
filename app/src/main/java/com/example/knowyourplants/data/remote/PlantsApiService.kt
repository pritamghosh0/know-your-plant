package com.example.knowyourplants.data.remote

import com.example.knowyourplants.Constants
import com.example.knowyourplants.data.remote.models.PlantDetails
import com.example.knowyourplants.data.remote.models.PlantListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantsApiService {
    @GET("/api/v2/species-list")
    suspend fun getPlants(
        @Query("key") key: String = Constants.KEY,
        @Query("page") page: Int
    ): PlantListResponse

    @GET("/api/v2/species/details/{plant_id}")
    suspend fun getSpeciesDetails(
        @Path("plant_id") plantId: String,
        @Query("key") key: String = Constants.KEY
    ): PlantDetails
}
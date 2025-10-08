package com.example.knowyourplants.data.remote

import com.example.knowyourplants.data.remote.models.user.User
import retrofit2.http.GET

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
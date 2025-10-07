package com.example.knowyourplants.data.remote

sealed class ApiResponse<out T> {
    object Loading : ApiResponse<Nothing>()

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Error(val message: String, val error: Throwable? = null) : ApiResponse<Nothing>()
}
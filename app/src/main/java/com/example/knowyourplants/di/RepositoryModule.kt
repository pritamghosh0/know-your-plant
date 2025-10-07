package com.example.knowyourplants.di

import com.example.knowyourplants.data.remote.PlantsApiService
import com.example.knowyourplants.repository.PlantsRepository
import com.example.knowyourplants.repository.PlantsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePlantsRepository(plantsApiService: PlantsApiService): PlantsRepository{
        return PlantsRepositoryImpl(plantsApiService)
    }
}
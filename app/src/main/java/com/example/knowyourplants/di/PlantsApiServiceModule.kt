package com.example.knowyourplants.di

import com.example.knowyourplants.Constants
import com.example.knowyourplants.data.remote.PlantsApiService
import com.example.knowyourplants.data.remote.UsersApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantsApiServiceModule {
    private val plantsBaseUrl = "https://perenual.com"
    private val usersApiBaseUrl = "https://jsonplaceholder.typicode.com"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(Constants.CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(Constants.CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
    }

    @Singleton
    @RetrofitPlantsApi
    @Provides
    fun provideRetrofitPlantsApi(retrofitBuilder: Retrofit.Builder): Retrofit{
        return retrofitBuilder.baseUrl(plantsBaseUrl).build()
    }

    @Singleton
    @RetrofitOthersApi
    @Provides
    fun provideRetrofitOtherApi(retrofitBuilder: Retrofit.Builder): Retrofit{
        return retrofitBuilder.baseUrl(usersApiBaseUrl).build()
    }

    @Singleton
    @Provides
    fun providePlantsApiService(@RetrofitPlantsApi retrofit: Retrofit): PlantsApiService{
        return retrofit.create(PlantsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUsersApiService(@RetrofitPlantsApi retrofit: Retrofit): UsersApiService{
        return retrofit.create(UsersApiService::class.java)
    }
}
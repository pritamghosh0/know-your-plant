package com.example.knowyourplants.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitPlantsApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitOthersApi
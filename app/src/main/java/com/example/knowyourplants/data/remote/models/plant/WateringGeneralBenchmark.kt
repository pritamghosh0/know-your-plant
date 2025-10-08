package com.example.knowyourplants.data.remote.models.plant

import com.google.gson.annotations.SerializedName

data class WateringGeneralBenchmark(
    @SerializedName("value" ) var value : String? = null,
    @SerializedName("unit"  ) var unit  : String? = null
)

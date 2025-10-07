package com.example.knowyourplants.data.remote.models

import com.google.gson.annotations.SerializedName

data class PlantDimensions(
    @SerializedName("type"      ) var type     : String? = null,
    @SerializedName("min_value" ) var minValue : Int?    = null,
    @SerializedName("max_value" ) var maxValue : Int?    = null,
    @SerializedName("unit"      ) var unit     : String? = null
)

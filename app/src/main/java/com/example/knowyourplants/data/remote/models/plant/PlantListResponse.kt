package com.example.knowyourplants.data.remote.models.plant

import com.google.gson.annotations.SerializedName

data class PlantListResponse(
    @SerializedName("data"         ) var data        : ArrayList<PlantListItem> = arrayListOf(),
    @SerializedName("to"           ) var to          : Int?            = null,
    @SerializedName("per_page"     ) var perPage     : Int?            = null,
    @SerializedName("current_page" ) var currentPage : Int?            = null,
    @SerializedName("from"         ) var from        : Int?            = null,
    @SerializedName("last_page"    ) var lastPage    : Int?            = null,
    @SerializedName("total"        ) var total       : Int?            = null
)

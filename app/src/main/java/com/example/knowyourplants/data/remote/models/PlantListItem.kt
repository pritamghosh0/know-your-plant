package com.example.knowyourplants.data.remote.models

import com.google.gson.annotations.SerializedName

data class PlantListItem(
    @SerializedName("id"              ) var id             : Int?              = null,
    @SerializedName("common_name"     ) var commonName     : String?           = null,
    @SerializedName("scientific_name" ) var scientificName : ArrayList<String> = arrayListOf(),
    @SerializedName("other_name"      ) var otherName      : ArrayList<String> = arrayListOf(),
    @SerializedName("family"          ) var family         : String?           = null,
    @SerializedName("subspecies"      ) var subspecies     : String?           = null,
    @SerializedName("cultivar"        ) var cultivar       : String?           = null,
    @SerializedName("variety"         ) var variety        : String?           = null,
    @SerializedName("species_epithet" ) var speciesEpithet : String?           = null,
    @SerializedName("genus"           ) var genus          : String?           = null,
    @SerializedName("default_image"   ) var defaultImage   : DefaultImage?     = DefaultImage()
)

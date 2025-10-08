package com.example.knowyourplants.data.remote.models.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("address") var address: Address? = Address(),
)

data class Address(
    @SerializedName("street") var street: String? = null,
    @SerializedName("suite") var suite: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("zipcode") var zipcode: String? = null,
    @SerializedName("geo") var geo: Geo? = Geo(),
)

data class Geo(
    @SerializedName("lat") var lat: String? = null,
    @SerializedName("lng") var lng: String? = null,
)

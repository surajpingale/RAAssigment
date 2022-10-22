package com.example.raassigment.model.dataclasses


import com.google.gson.annotations.SerializedName

data class Facilities(
    @SerializedName("exclusions")
    val exclusions: List<List<Exclusion>>,
    @SerializedName("facilities")
    val facilities: List<Facility>
)
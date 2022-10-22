package com.example.raassigment.model.dataclasses


import com.google.gson.annotations.SerializedName

data class Facility(
    @SerializedName("facility_id")
    val facilityId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("options")
    val options: List<Option>,
    var index : Int
)
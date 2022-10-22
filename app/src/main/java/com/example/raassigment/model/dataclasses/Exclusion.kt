package com.example.raassigment.model.dataclasses


import com.google.gson.annotations.SerializedName


data class Exclusion(
    @SerializedName("facility_id")
    var facilityId: String,
    @SerializedName("options_id")
    var optionsId: String
)
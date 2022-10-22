package com.example.raassigment.model.dataclasses


import com.example.raassigment.utils.Constants
import com.google.gson.annotations.SerializedName

data class Option(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    var isSelected: Int = Constants.UNSELECTED,
//    var exAdaptPos: Int? = null,
    var exclusion: ArrayList<Exclusion>? = null
//    var exclusionFacilityId: String,
//    var exclusionOptionId: String
)
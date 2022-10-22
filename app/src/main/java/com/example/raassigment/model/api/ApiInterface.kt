package com.example.raassigment.model.api

import com.example.raassigment.model.dataclasses.Facilities
import com.example.raassigment.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET(Constants.END_POINT)
    suspend fun getFacilities() : Response<Facilities>

}
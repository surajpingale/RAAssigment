package com.example.raassigment.model.repository

import com.example.raassigment.model.api.ApiInterface
import com.example.raassigment.model.dataclasses.Facilities
import retrofit2.Response
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val apiInterface: ApiInterface ) {

    suspend fun getAllFacilities(): Response<Facilities> {
        return apiInterface.getFacilities()
    }
}
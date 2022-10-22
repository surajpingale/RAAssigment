package com.example.raassigment.viewmodel

import androidx.lifecycle.*
import com.example.raassigment.model.dataclasses.Facilities
import com.example.raassigment.model.dataclasses.Option
import com.example.raassigment.model.repository.FacilityRepository
import com.example.raassigment.utils.DataStore
import com.example.raassigment.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.FieldPosition
import javax.inject.Inject

class FacilityViewModel (
    private val repository: FacilityRepository,
    private val dataStore: DataStore
) : ViewModel() {

    private var _dataFromApi = MutableLiveData<Response<Facilities>>()

    val getData: LiveData<Response<Facilities>>
        get() = _dataFromApi

    var lastFacilityPosition = dataStore.readLastFacilityPos
    var lastOptionsPosition = dataStore.readLastOptionsPos
    var lastAdapterPos = dataStore.readLastAdapterPos

    init {
        getData()
    }

    fun getFacilities() = liveData {
        emit(Response.Loading())
        val result = repository.getAllFacilities()
        if (result.isSuccessful && result.body() != null) {
            emit(Response.Success(result.body()))
        }
    }

    private fun getData() {
        viewModelScope.launch {
            val result = repository.getAllFacilities()
            if (result.isSuccessful && result.body() != null) {

                _dataFromApi.postValue(Response.Success(result.body()!!))
            } else {
                _dataFromApi.postValue(Response.Error("Something wrong"))
            }
        }
    }

    fun writeLastSelection(facilityPosition: Int, optionPosition: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.writeToLocal(facilityPosition, optionPosition)
        }
    }

    fun clearDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.clearData()
        }
    }


}
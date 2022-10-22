package com.example.raassigment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.raassigment.model.repository.FacilityRepository
import com.example.raassigment.utils.DataStore
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val repository: FacilityRepository,
    private val dataStore: DataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FacilityViewModel(repository, dataStore) as T
    }
}
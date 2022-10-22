package com.example.raassigment.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStore @Inject constructor(
    private val context: Context
) {

    object PreferenceKey {

        val lastFacilityPos = intPreferencesKey("lastFacilityPos")
        val lastOptionsPos = intPreferencesKey("lastOptionPos")
        val lastAdapterPos = intPreferencesKey("lastAdapterPos")

    }

    private val Context.dataStore by preferencesDataStore("datastore")

    suspend fun writeToLocal(
        lastFacilityId: Int,
        lastOptionsId: Int
    ) {
        context.dataStore.edit { preferences ->

            preferences[PreferenceKey.lastFacilityPos] = lastFacilityId
            preferences[PreferenceKey.lastOptionsPos] = lastOptionsId
        }
    }

    val readLastFacilityPos: Flow<Int> =
        context.dataStore.data.catch {
            if (this is Exception) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            val lastFacilityId = preferences[PreferenceKey.lastFacilityPos] ?: 0
            lastFacilityId
        }

    val readLastOptionsPos: Flow<Int> =
        context.dataStore.data.catch {
            if (this is Exception) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            val lastOptionsId = preferences[PreferenceKey.lastOptionsPos] ?: 0
            lastOptionsId
        }

    val readLastAdapterPos: Flow<Int> =
        context.dataStore.data.catch {
            if (this is Exception) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            val lastOptionsId = preferences[PreferenceKey.lastAdapterPos] ?: 0
            lastOptionsId
        }

    suspend fun clearData()
    {
        context.dataStore.edit {  preferences ->
            preferences.remove(PreferenceKey.lastFacilityPos)
            preferences.remove(PreferenceKey.lastOptionsPos)
        }
    }


}
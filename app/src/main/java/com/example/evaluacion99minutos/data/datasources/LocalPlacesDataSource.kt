package com.example.evaluacion99minutos.data.datasources

import com.example.evaluacion99minutos.data.database.Place
import kotlinx.coroutines.flow.Flow

interface LocalPlacesDataSource {

    val currentPlaces: Flow<List<Place>>

    suspend fun delete(place: Place)

    suspend fun getById(placeId: Int): Place?

    suspend fun save(place: Place)
}
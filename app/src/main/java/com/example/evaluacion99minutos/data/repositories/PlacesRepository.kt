package com.example.evaluacion99minutos.data.repositories

import com.example.evaluacion99minutos.data.database.Place
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {

    val currentPlaces: Flow<List<Place>>

    suspend fun delete(place: Place)

    suspend fun getById(placeId: Int): Place?

    suspend fun save(place: Place)

}

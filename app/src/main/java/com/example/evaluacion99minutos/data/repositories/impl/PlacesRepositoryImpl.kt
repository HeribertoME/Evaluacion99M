package com.example.evaluacion99minutos.data.repositories.impl

import com.example.evaluacion99minutos.data.database.Place
import com.example.evaluacion99minutos.data.datasources.LocalPlacesDataSource
import com.example.evaluacion99minutos.data.repositories.PlacesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val localPlacesDataSource: LocalPlacesDataSource
) : PlacesRepository {

    override val currentPlaces: Flow<List<Place>> = localPlacesDataSource.currentPlaces

    override suspend fun delete(place: Place) {
        localPlacesDataSource.delete(place)
    }

    override suspend fun getById(placeId: Int): Place? = localPlacesDataSource.getById(placeId)

    override suspend fun save(place: Place) {
        localPlacesDataSource.save(place)
    }
}
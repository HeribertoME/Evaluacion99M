package com.example.evaluacion99minutos.data.datasources.impl

import com.example.evaluacion99minutos.data.database.Place
import com.example.evaluacion99minutos.data.database.PlaceDao
import com.example.evaluacion99minutos.data.datasources.LocalPlacesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalPlacesDataSourceImpl @Inject constructor(
    private val placeDao: PlaceDao
) : LocalPlacesDataSource {

    override val currentPlaces: Flow<List<Place>> = placeDao.getAll()

    override suspend fun delete(place: Place) {
        placeDao.delete(place)
    }

    override suspend fun getById(placeId: Int): Place? = placeDao.getById(placeId)

    override suspend fun save(place: Place) {
        placeDao.insert(place)
    }
}
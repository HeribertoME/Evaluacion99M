package com.example.evaluacion99minutos.domain

import com.example.evaluacion99minutos.data.repositories.PlacesRepository
import com.example.evaluacion99minutos.data.database.Place
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentPlacesUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {

    operator fun invoke(): Flow<List<Place>> = placesRepository.currentPlaces

}

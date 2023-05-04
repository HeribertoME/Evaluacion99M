package com.example.evaluacion99minutos.domain

import com.example.evaluacion99minutos.data.database.Place
import com.example.evaluacion99minutos.data.repositories.PlacesRepository
import javax.inject.Inject

class DeletePlaceUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {

    suspend operator fun invoke(place: Place) {
        placesRepository.delete(place)
    }

}
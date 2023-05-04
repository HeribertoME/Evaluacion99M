package com.example.evaluacion99minutos.domain

import com.example.evaluacion99minutos.data.repositories.PlacesRepository
import javax.inject.Inject

class GetPlaceByIdUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {

    suspend operator fun invoke(placeId: Int) = placesRepository.getById(placeId)
}
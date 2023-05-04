package com.example.evaluacion99minutos.framework.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion99minutos.data.database.Place
import com.example.evaluacion99minutos.domain.GetPlaceByIdUseCase
import com.example.evaluacion99minutos.domain.SavePlaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val getPlaceByIdUseCase: GetPlaceByIdUseCase,
    private val savePlaceUseCase: SavePlaceUseCase,
    private val placeId: Int,
    private val placeName: String
) : ViewModel() {

    private val _state = MutableStateFlow(Place(0, placeName, "", ""))
    val state: StateFlow<Place> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val place = getPlaceByIdUseCase(placeId)
            if (place != null) {
                _state.value = place
            }
        }
    }

    fun save(name: String, description: String) {
        viewModelScope.launch {
            val place = _state.value.copy(name = name, description = description)
            savePlaceUseCase(place)
        }
    }

}
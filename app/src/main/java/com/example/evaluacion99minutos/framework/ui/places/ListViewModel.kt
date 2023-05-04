package com.example.evaluacion99minutos.framework.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion99minutos.data.database.Place
import com.example.evaluacion99minutos.domain.DeletePlaceUseCase
import com.example.evaluacion99minutos.domain.GetCurrentPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getCurrentPlacesUseCase: GetCurrentPlacesUseCase,
    private val deletePlaceUseCase: DeletePlaceUseCase,
) : ViewModel() {

    val state = getCurrentPlacesUseCase()

    fun onPlaceDelete(place: Place) {
        viewModelScope.launch {
            deletePlaceUseCase(place)
        }
    }

}

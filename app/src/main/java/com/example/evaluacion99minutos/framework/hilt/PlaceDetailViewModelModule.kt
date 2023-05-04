package com.example.evaluacion99minutos.framework.hilt

import androidx.lifecycle.SavedStateHandle
import com.example.evaluacion99minutos.framework.ui.places.PlaceDetailFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PlaceDetailViewModelModule {

    @Provides
    fun providePlaceId(savedStateHandle: SavedStateHandle) =
        requireNotNull(savedStateHandle.get<Int>(PlaceDetailFragment.EXTRA_PLACE_ID))

    @Provides
    fun providePlaceName(savedStateHandle: SavedStateHandle) =
        requireNotNull(savedStateHandle.get<String>(PlaceDetailFragment.EXTRA_PLACE_NAME))
}
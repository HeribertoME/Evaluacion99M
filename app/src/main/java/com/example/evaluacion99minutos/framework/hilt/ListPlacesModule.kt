package com.example.evaluacion99minutos.framework.hilt

import com.example.evaluacion99minutos.data.database.PlaceDao
import com.example.evaluacion99minutos.data.datasources.LocalPlacesDataSource
import com.example.evaluacion99minutos.data.datasources.impl.LocalPlacesDataSourceImpl
import com.example.evaluacion99minutos.data.repositories.PlacesRepository
import com.example.evaluacion99minutos.data.repositories.impl.PlacesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListPlacesModule {

    @Provides
    @Singleton
    fun provideLocalPlacesDataSource(
        placeDao: PlaceDao
    ): LocalPlacesDataSource = LocalPlacesDataSourceImpl(placeDao)

    @Provides
    @Singleton
    fun providePlacesRepository(
        localPlacesDataSource: LocalPlacesDataSource
    ): PlacesRepository = PlacesRepositoryImpl(localPlacesDataSource)

}
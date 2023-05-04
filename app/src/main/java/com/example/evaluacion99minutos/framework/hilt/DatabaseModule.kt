package com.example.evaluacion99minutos.framework.hilt

import android.app.Application
import androidx.room.Room
import com.example.evaluacion99minutos.data.database.PlacesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePlacesDatabase(app: Application) = Room.databaseBuilder(
        app,
        PlacesDatabase::class.java,
        "places.db"
    ).build()

    @Provides
    fun providePlacesDao(db: PlacesDatabase) = db.placesDao()

}
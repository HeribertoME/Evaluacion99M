package com.example.evaluacion99minutos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [Place::class]
)
abstract class PlacesDatabase : RoomDatabase() {

    abstract fun placesDao(): PlaceDao
}
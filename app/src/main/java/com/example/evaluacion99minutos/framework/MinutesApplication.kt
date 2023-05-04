package com.example.evaluacion99minutos.framework

import androidx.multidex.MultiDexApplication
import com.example.evaluacion99minutos.data.database.PlacesDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MinutesApplication : MultiDexApplication() {

    lateinit var placesDatabase: PlacesDatabase
        private set

    override fun onCreate() {
        super.onCreate()
    }
}
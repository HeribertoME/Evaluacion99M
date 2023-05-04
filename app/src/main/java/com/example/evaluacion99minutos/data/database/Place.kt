package com.example.evaluacion99minutos.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val alias: String,
    val description: String,
)

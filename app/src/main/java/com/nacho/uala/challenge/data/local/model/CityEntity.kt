package com.nacho.uala.challenge.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nacho.uala.challenge.domain.model.City

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val isFavorite: Boolean = false
)

fun CityEntity.toDomain(): City = City(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
    isFavorite = isFavorite
)
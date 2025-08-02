package com.nacho.uala.challenge.domain.model

import com.nacho.uala.challenge.data.local.model.CityEntity

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    var isFavorite: Boolean
)

fun City.toEntity(): CityEntity = CityEntity(
    id = id,
    name = name,
    country = country,
    lat = lat,
    lon = lon,
    isFavorite = isFavorite
)
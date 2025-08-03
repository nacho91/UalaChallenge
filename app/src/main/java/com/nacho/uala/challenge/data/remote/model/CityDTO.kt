package com.nacho.uala.challenge.data.remote.model

import com.google.gson.annotations.SerializedName
import com.nacho.uala.challenge.domain.model.City

data class CityDTO(
    @SerializedName("_id") val id: Int,
    val country: String,
    val name: String,
    @SerializedName("coord") val coordinates: CoordinatesDTO
)

fun CityDTO.toDomain(): City = City(
    id = id,
    country = country,
    name = name,
    lat = coordinates.lat,
    lon = coordinates.lon,
    isFavorite = false
)
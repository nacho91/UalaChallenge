package com.nacho.uala.challenge.data.remote.model

import com.google.gson.annotations.SerializedName

data class CityDTO(
    @SerializedName("_id") val id: Int,
    val country: String,
    val name: String,
    @SerializedName("coord") val coordinates: CoordinatesDTO
)

data class CoordinatesDTO(
    val lat: Double,
    val long: Double
)
package com.nacho.uala.challenge.domain.model

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val isFavorite: Boolean
)

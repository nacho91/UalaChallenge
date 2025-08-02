package com.nacho.uala.challenge.data.remote

import com.nacho.uala.challenge.data.remote.model.CityDTO
import retrofit2.http.GET

interface CityApi {

    @GET("cities.json")
    suspend fun getCities(): List<CityDTO>
}
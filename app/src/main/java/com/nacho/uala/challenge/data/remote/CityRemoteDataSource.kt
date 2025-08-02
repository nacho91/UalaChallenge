package com.nacho.uala.challenge.data.remote

import com.nacho.uala.challenge.data.remote.model.CityDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRemoteDataSource @Inject constructor(private val api: CityApi) {

    suspend fun fetchCities(): List<CityDTO> {
        return api.getCities()
    }
}
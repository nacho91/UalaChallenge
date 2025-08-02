package com.nacho.uala.challenge.data.local

import com.nacho.uala.challenge.data.remote.model.CityDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityLocalDataStore @Inject constructor(
    private val dao: CityDao
) {

    suspend fun saveAll(cities: List<CityDTO>) {

    }
}
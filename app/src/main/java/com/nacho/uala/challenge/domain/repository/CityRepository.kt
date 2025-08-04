package com.nacho.uala.challenge.domain.repository

import androidx.paging.PagingData
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun fetchCities(): Result<List<City>>

    fun getCities(): Flow<PagingData<City>>

    suspend fun getCityById(id: Int): Result<City>

    suspend fun saveCities(cities: List<City>): Result<Unit>

    suspend fun toggleCityFavorite(city: City): Result<Unit>

    suspend fun isLocalEmpty(): Result<Boolean>
}
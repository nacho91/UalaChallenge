package com.nacho.uala.challenge.data.local

import com.nacho.uala.challenge.data.local.model.CityEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityLocalDataSource @Inject constructor(
    private val dao: CityDao
) {

    fun getCities(): Flow<List<CityEntity>> {
        return dao.getCities()
    }

    suspend fun saveAll(cities: List<CityEntity>) {
        dao.insertAll(cities)
    }

    suspend fun update(city: CityEntity) {
        dao.update(city)
    }

    suspend fun isEmpty(): Boolean {
        return dao.count() == 0
    }
}
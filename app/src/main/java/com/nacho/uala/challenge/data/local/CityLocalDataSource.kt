package com.nacho.uala.challenge.data.local

import com.nacho.uala.challenge.data.local.model.CityEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityLocalDataSource @Inject constructor(
    private val dao: CityDao
) {

    suspend fun getCities(query: String, limit: Int, offset: Int): List<CityEntity> {
        return dao.getCities(query, limit, offset)
    }

    suspend fun getFavoritesCities(query: String, limit: Int, offset: Int): List<CityEntity> {
        return dao.getFavoriteCities(query, limit, offset)
    }

    suspend fun getCityById(id: Int): CityEntity? {
        return dao.getCityById(id)
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
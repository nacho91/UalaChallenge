package com.nacho.uala.challenge.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nacho.uala.challenge.data.local.CityLocalDataSource
import com.nacho.uala.challenge.data.local.CityLocalPagingSource
import com.nacho.uala.challenge.data.local.model.toDomain
import com.nacho.uala.challenge.data.remote.CityRemoteDataSource
import com.nacho.uala.challenge.data.remote.model.toDomain
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.model.toEntity
import com.nacho.uala.challenge.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val remote: CityRemoteDataSource,
    private val local: CityLocalDataSource
): CityRepository {

    override suspend fun fetchCities(): Result<List<City>> {
        return try {
            val data = remote.fetchCities().map { it.toDomain() }
            Result.Success(data)
        } catch (e: IOException) {
            Result.Error.Network(e)
        } catch (e: Exception) {
            Result.Error.Unknown(e)
        }
    }

    override fun getCities(query: String, onlyFavorites: Boolean): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { CityLocalPagingSource(local, query, onlyFavorites, PAGE_SIZE) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getCityById(id: Int): Result<City> {
        return try {
            val city = local.getCityById(id)

            if (city != null) {
                Result.Success(city.toDomain())
            } else {
                Result.Error.Database(Throwable("City not found"))
            }
        } catch (e: Throwable) {
            Result.Error.Database(e)
        }
    }

    override suspend fun saveCities(cities: List<City>): Result<Unit> {
        return try {
            local.saveAll(cities.map { it.toEntity() })
            Result.Success(Unit)
        } catch (e: Throwable) {
            Result.Error.Database(e)
        }
    }

    override suspend fun toggleCityFavorite(city: City): Result<Unit> {
        return try {
            val updatedCity = city.copy(isFavorite = !city.isFavorite)
            local.update(updatedCity.toEntity())
            Result.Success(Unit)
        } catch (e: Throwable) {
            Result.Error.Database(e)
        }
    }

    override suspend fun isLocalEmpty(): Result<Boolean> {
        return try {
            val isEmpty = local.isEmpty()
            Result.Success(isEmpty)
        } catch (e: Throwable) {
            Result.Error.Database(e)
        }
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}
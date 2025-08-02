package com.nacho.uala.challenge.domain.repository

import com.nacho.uala.challenge.data.local.CityLocalDataSource
import com.nacho.uala.challenge.data.local.model.CityEntity
import com.nacho.uala.challenge.data.local.model.toDomain
import com.nacho.uala.challenge.data.remote.CityRemoteDataSource
import com.nacho.uala.challenge.data.remote.model.toDomain
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.model.toEntity
import com.nacho.uala.challenge.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

    override fun getCities(): Flow<Result<List<City>>> {
        return local.getCities()
            .map<List<CityEntity>, Result<List<City>>> { list ->
                Result.Success(list.map { it.toDomain() })
            }
            .catch { e ->
                emit(Result.Error.Database(e))
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
}
package com.nacho.uala.challenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nacho.uala.challenge.data.local.model.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM cities WHERE name LIKE '%' || :query || '%' ORDER BY name ASC, country ASC LIMIT :limit OFFSET :offset")
    suspend fun getCities(query: String, limit: Int, offset: Int): List<CityEntity>

    @Query("SELECT * FROM cities WHERE isFavorite = 1 AND name LIKE '%' || :query || '%' ORDER BY name ASC, country ASC LIMIT :limit OFFSET :offset")
    suspend fun getFavoriteCities(query: String, limit: Int, offset: Int): List<CityEntity>

    @Query("SELECT * FROM cities WHERE id = :id LIMIT 1")
    suspend fun getCityById(id: Int): CityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Update
    suspend fun update(city: CityEntity)

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun count(): Int
}
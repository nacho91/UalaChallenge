package com.nacho.uala.challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nacho.uala.challenge.data.local.model.CityEntity

@Database(entities = [CityEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao() : CityDao
}
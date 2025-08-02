package com.nacho.uala.challenge.di

import android.content.Context
import androidx.room.Room
import com.nacho.uala.challenge.data.local.AppDatabase
import com.nacho.uala.challenge.data.local.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "cities.db"
        ).fallbackToDestructiveMigration(false).build()

    @Provides
    @Singleton
    fun provideCityDao(database: AppDatabase): CityDao =
        database.cityDao()
}
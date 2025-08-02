package com.nacho.uala.challenge.di.modules

import com.nacho.uala.challenge.data.local.CityLocalDataSource
import com.nacho.uala.challenge.data.remote.CityRemoteDataSource
import com.nacho.uala.challenge.domain.repository.CityRepository
import com.nacho.uala.challenge.domain.repository.CityRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCityRepository(
        remoteDataSource: CityRemoteDataSource,
        localDataSource: CityLocalDataSource
    ): CityRepository {
        return CityRepositoryImpl(remoteDataSource, localDataSource)

    }
}
package com.nacho.uala.challenge.domain.usecase

import com.nacho.uala.challenge.di.qualifiers.IoDispatcher
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.repository.CityRepository
import com.nacho.uala.challenge.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCityByIdUseCase @Inject constructor(
    private val repository: CityRepository,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(cityId: Int): Result<City> = withContext(dispatcher) {
        repository.getCityById(cityId)
    }
}
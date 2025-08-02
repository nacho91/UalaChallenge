package com.nacho.uala.challenge.domain.usecase

import com.nacho.uala.challenge.di.qualifiers.IoDispatcher
import com.nacho.uala.challenge.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.nacho.uala.challenge.domain.util.Result

class InitializeCitiesUseCase @Inject constructor(
    private val repository: CityRepository,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Result<Unit> = withContext(dispatcher) {
        when (val isEmptyResult = repository.isLocalEmpty()) {
            is Result.Success -> {
                if (isEmptyResult.data) {
                    when (val fetchResult = repository.fetchCities()) {
                        is Result.Success -> repository.saveCities(fetchResult.data)
                        is Result.Error -> fetchResult
                    }
                } else {
                    Result.Success(Unit)
                }
            }
            is Result.Error -> isEmptyResult
        }
    }
}
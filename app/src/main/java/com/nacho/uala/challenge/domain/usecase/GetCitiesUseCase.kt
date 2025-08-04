package com.nacho.uala.challenge.domain.usecase

import androidx.paging.PagingData
import com.nacho.uala.challenge.di.qualifiers.IoDispatcher
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.repository.CityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCitiesUseCase @Inject constructor(
    private val repository: CityRepository,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(query: String, onlyFavorites: Boolean): Flow<PagingData<City>> {
        return repository.getCities(query, onlyFavorites).flowOn(dispatcher)
    }
}
package com.nacho.uala.challenge.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.usecase.GetCitiesUseCase
import com.nacho.uala.challenge.domain.usecase.ToggleCityFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val toggleCityFavoriteUseCase: ToggleCityFavoriteUseCase
) : ViewModel() {

    private val favoritesCityMap = mutableMapOf<Int, MutableStateFlow<Boolean>>()

    val cities: Flow<PagingData<CityUiState>> =
        getCitiesUseCase()
            .map { pagingData ->
                pagingData.map { city ->
                    val stateFlow = favoritesCityMap.getOrPut(city.id) {
                        MutableStateFlow(city.isFavorite)
                    }
                    CityUiState(city, stateFlow)
                }
            }
            .cachedIn(viewModelScope)

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity

    fun onToggleCityFavorite(cityUiState: CityUiState) {
        favoritesCityMap[cityUiState.city.id]?.let { state ->
            state.value = !state.value
        }
        viewModelScope.launch {
            toggleCityFavoriteUseCase(cityUiState.city)
        }
    }

    fun onCitySelected(city: City) {
        _selectedCity.value = city
    }
}

data class CityUiState(
    val city: City,
    val isFavorite: StateFlow<Boolean>
)
package com.nacho.uala.challenge.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.repository.CityRepository
import com.nacho.uala.challenge.domain.usecase.GetCitiesUseCase
import com.nacho.uala.challenge.domain.usecase.ToggleCityFavoriteUseCase
import com.nacho.uala.challenge.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val toggleCityFavoriteUseCase: ToggleCityFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _state

    init {
        getCitiesUseCase()
            .onEach { result ->
                if (result is Result.Success) {
                    _state.update { it.copy(isLoading = false, cities = result.data) }
                } else {
                    _state.update { it.copy(isLoading = false, isError = true) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onToggleCityFavorite(city: City) {
        viewModelScope.launch {
            toggleCityFavoriteUseCase(city)
        }
    }
}

data class ListUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val cities: List<City>? = null
)
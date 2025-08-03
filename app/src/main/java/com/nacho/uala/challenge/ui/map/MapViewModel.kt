package com.nacho.uala.challenge.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.usecase.GetCityByIdUseCase
import com.nacho.uala.challenge.domain.util.Result
import com.nacho.uala.challenge.ui.list.ListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getCityByIdUseCase: GetCityByIdUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _state
    
    fun loadCity(cityId: Int) {
        viewModelScope.launch {
            when(val result = getCityByIdUseCase(cityId)) {
                is Result.Success -> {
                    _state.update { _state.value.copy(city = result.data) }
                }
                else -> {
                    _state.update { _state.value.copy(city = null, isError = true) }
                }
            }
        }
    }
}

data class MapUiState(
    val isError: Boolean = false,
    val city: City? = null
)
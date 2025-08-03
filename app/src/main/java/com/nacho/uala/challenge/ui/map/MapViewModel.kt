package com.nacho.uala.challenge.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.uala.challenge.domain.usecase.GetCityByIdUseCase
import com.nacho.uala.challenge.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getCityByIdUseCase: GetCityByIdUseCase
): ViewModel() {

    
    fun loadCity(cityId: Int) {
        viewModelScope.launch {
            when(val result = getCityByIdUseCase(cityId)) {
                is Result.Success -> {

                }
                else -> {

                }
            }
        }
    }
}
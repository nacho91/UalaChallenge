package com.nacho.uala.challenge.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.uala.challenge.domain.usecase.InitializeCitiesUseCase
import com.nacho.uala.challenge.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: InitializeCitiesUseCase
): ViewModel() {

    private val _state = MutableStateFlow<SplashUiState>(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _state

    init {
        initializeCities()
    }

    fun initializeCities() {
        viewModelScope.launch {
            _state.value = SplashUiState(
                isLoading = true,
            )
            when(useCase()) {
                is Result.Success -> {
                    _state.value = SplashUiState(
                        isLoading = false,
                        isSuccess = true
                    )
                }
                is Result.Error -> {
                    _state.value = SplashUiState(
                        isLoading = false,
                        isError = true
                    )
                }
            }

        }
    }
}

data class SplashUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false
)
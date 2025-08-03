package com.nacho.uala.challenge.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.nacho.uala.challenge.R.string.error
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.ui.map.components.CityMap

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    cityId: Int,
    viewModel: MapViewModel = hiltViewModel()
) {
    LaunchedEffect(cityId) {
        viewModel.loadCity(cityId)
    }

    val state by viewModel.uiState.collectAsState()

    when {
        state.isError -> {
            MapError(
                modifier = modifier
            )
        }
        state.city != null -> {
            MapContent(
                modifier = modifier,
                city = state.city!!
            )
        }
    }

}

@Composable
fun MapContent(
    modifier: Modifier = Modifier,
    city: City
) {
    CityMap(
        modifier = modifier.fillMaxSize(),
        city = city
    )
}

@Composable
fun MapError(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(error),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
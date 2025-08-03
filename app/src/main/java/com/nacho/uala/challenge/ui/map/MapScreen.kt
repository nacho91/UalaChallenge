package com.nacho.uala.challenge.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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

    CityMap(
        modifier = modifier.fillMaxSize(),
        city = null
    )
}
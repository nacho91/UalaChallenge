package com.nacho.uala.challenge.ui.list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nacho.uala.challenge.domain.model.City

@Composable
fun PortraitCityList(
    modifier: Modifier = Modifier,
    cities: List<City>,
    onCityClick: (City) -> Unit,
    onCityToggleFavorite: (City) -> Unit
) {
    CityList(
        modifier = modifier,
        cities = cities,
        onClick = onCityClick,
        onToggleFavorite = onCityToggleFavorite
    )
}

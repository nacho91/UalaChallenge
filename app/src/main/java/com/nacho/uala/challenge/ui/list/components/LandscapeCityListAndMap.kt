package com.nacho.uala.challenge.ui.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.nacho.uala.challenge.domain.model.City

@Composable
fun LandscapeListAndMap(
    modifier: Modifier = Modifier,
    cities: List<City>,
    onCityClick: (City) -> Unit,
    onCityToggleFavorite: (City) -> Unit
) {
    Row(
        modifier = modifier.testTag("city_list_container")
    ) {
        CityList(
            modifier = Modifier.weight(1f),
            cities = cities,
            onClick = onCityClick,
            onToggleFavorite = onCityToggleFavorite
        )

        Box(
            modifier = Modifier.weight(2f).testTag("city_map_container")
        ) {
            // TODO: implement map view
        }
    }
}
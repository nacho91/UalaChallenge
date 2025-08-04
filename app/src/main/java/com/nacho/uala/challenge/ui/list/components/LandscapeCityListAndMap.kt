package com.nacho.uala.challenge.ui.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.nacho.uala.challenge.R.string.map_empty_state
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.ui.list.CityUiState
import com.nacho.uala.challenge.ui.map.components.CityMap

@Composable
fun LandscapeListAndMap(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    cities: LazyPagingItems<CityUiState>,
    selectedCity: City?,
    onCityClick: (City) -> Unit,
    onCityToggleFavorite: (CityUiState) -> Unit
) {
    Row(
        modifier = modifier.testTag("city_list_container")
    ) {
        CityList(
            modifier = Modifier.weight(1f),
            listState = listState,
            cities = cities,
            onClick = onCityClick,
            onToggleFavorite = onCityToggleFavorite
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
                .testTag("city_map_container"),
            contentAlignment = Alignment.Center
        ) {
            if (selectedCity != null) {
                CityMap(
                    city = selectedCity
                )
            } else {
                Text(
                    text = stringResource(map_empty_state),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}
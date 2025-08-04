package com.nacho.uala.challenge.ui.list.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.ui.list.CityUiState

@Composable
fun PortraitCityList(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    cities: LazyPagingItems<CityUiState>,
    onCityClick: (City) -> Unit,
    onCityToggleFavorite: (CityUiState) -> Unit,
    onCityDetailClick: (City) -> Unit
) {
    CityList(
        modifier = modifier,
        listState = listState,
        cities = cities,
        onClick = onCityClick,
        onToggleFavorite = onCityToggleFavorite,
        onCityDetailClick = onCityDetailClick
    )
}

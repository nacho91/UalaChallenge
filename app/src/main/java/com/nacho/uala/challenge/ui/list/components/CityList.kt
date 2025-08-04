package com.nacho.uala.challenge.ui.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.nacho.uala.challenge.R.string.city_title
import com.nacho.uala.challenge.R.string.city_coordinates
import com.nacho.uala.challenge.R.string.city_favorite_button_desc
import com.nacho.uala.challenge.R.string.error
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.ui.list.CityUiState
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.paging.compose.collectAsLazyPagingItems
import com.nacho.uala.challenge.R
import com.nacho.uala.challenge.R.string.city_info_button_desc
import com.nacho.uala.challenge.ui.component.FavoriteButton
import kotlinx.coroutines.flow.flowOf

@Composable
fun CityList(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    cities: LazyPagingItems<CityUiState>,
    onClick: (City) -> Unit,
    onToggleFavorite: (CityUiState) -> Unit,
    onCityDetailClick: (City) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            count = cities.itemCount,
            key = { index -> cities.peek(index)?.city?.id ?: index }
        ) { index ->
            val cityUiState = cities[index]
            if (cityUiState != null) {
                CityItem(
                    modifier = Modifier.testTag("city_item"),
                    cityUiState = cityUiState,
                    onClick = { onClick(cityUiState.city) },
                    onToggleFavorite = {
                        onToggleFavorite(cityUiState)
                    },
                    onCityDetailClick = {
                        onCityDetailClick(cityUiState.city)
                    }
                )
            }
        }

        when (cities.loadState.append) {
            is LoadState.Loading -> item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(error)
                )
            }

            else -> Unit
        }
    }
}


@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    cityUiState: CityUiState,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onCityDetailClick: () -> Unit
) {
    val isFavorite by cityUiState.isFavorite.collectAsState()

    val city = cityUiState.city

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Text(
                    text = stringResource(city_title, city.name, city.country),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = stringResource(city_coordinates, city.lat, city.lon),
                style = MaterialTheme.typography.bodySmall
            )
        }
        FavoriteButton(
            modifier = Modifier.testTag("favorite_button"),
            contentDescription = stringResource(city_favorite_button_desc),
            isFavorite = isFavorite,
            onToggleFavorite = onToggleFavorite
        )
        IconButton(
            modifier = Modifier.testTag("info_button"),
            onClick = onCityDetailClick
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(city_info_button_desc)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CityListPreview() {
    val cities = listOf(
        CityUiState(
            city = City(
                id = 0,
                country = "AR",
                name = "Argentina",
                lat = 0.0,
                lon = 0.0,
                isFavorite = false
            ),
            isFavorite = MutableStateFlow(false)
        ),
        CityUiState(
            city = City(
                id = 1,
                country = "UY",
                name = "Uruguay",
                lat = 0.0,
                lon = 0.0,
                isFavorite = true
            ),
            isFavorite = MutableStateFlow(true)
        )
    )

    val pagingData = PagingData.from(cities)
    val lazyPagingCities: LazyPagingItems<CityUiState> =
        flowOf(pagingData).collectAsLazyPagingItems()

    CityList(
        modifier = Modifier.fillMaxSize(),
        listState = rememberLazyListState(),
        cities = lazyPagingCities,
        onClick = {},
        onCityDetailClick = {},
        onToggleFavorite = {}
    )
}
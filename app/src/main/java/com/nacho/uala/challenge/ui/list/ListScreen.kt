package com.nacho.uala.challenge.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nacho.uala.challenge.R
import com.nacho.uala.challenge.R.string.initializing
import com.nacho.uala.challenge.R.string.retry_button
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.ui.list.components.LandscapeListAndMap
import com.nacho.uala.challenge.ui.list.components.PortraitCityList

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
    navigateMap: (City) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()

    when {
        state.isLoading -> {
            ListLoading(modifier = modifier)
        }
        state.isError -> {
            ListError(modifier = modifier)
        }
        state.cities != null -> {
            ListContent(
                modifier = modifier,
                cities = state.cities!!,
                selectedCity = selectedCity,
                navigateMap = { city ->
                    navigateMap(city)
                },
                onCityClick = { city ->
                    viewModel.onCitySelected(city)
                },
                onToggleCityFavorite = { city ->
                    viewModel.onToggleCityFavorite(city)
                }
            )
        }
    }
}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    cities: List<City>,
    selectedCity: City?,
    navigateMap: (City) -> Unit,
    onCityClick: (City) -> Unit,
    onToggleCityFavorite: (City) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeListAndMap(
            modifier = modifier.fillMaxSize(),
            cities = cities,
            selectedCity = selectedCity,
            onCityClick = { city ->
                onCityClick(city)
            },
            onCityToggleFavorite = onToggleCityFavorite,

            )
    } else {
        PortraitCityList(
            modifier = modifier.fillMaxSize().testTag("city_list_container"),
            cities = cities,
            onCityClick = { city ->
                onCityClick(city)
                navigateMap(city)
            },
            onCityToggleFavorite = onToggleCityFavorite
        )
    }
}

@Composable
fun ListLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ListError(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
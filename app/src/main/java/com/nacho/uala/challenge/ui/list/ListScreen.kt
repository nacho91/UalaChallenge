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
    onCityClick: (City) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

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
                onCityClick = onCityClick,
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
    onCityClick: (City) -> Unit,
    onToggleCityFavorite: (City) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeListAndMap(
            modifier = modifier.fillMaxSize(),
            cities = cities,
            onCityClick = {
                // TODO: implement map refresh
            },
            onCityToggleFavorite = onToggleCityFavorite,

            )
    } else {
        PortraitCityList(
            modifier = modifier.fillMaxSize().testTag("city_list_container"),
            cities = cities,
            onCityClick = onCityClick,
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
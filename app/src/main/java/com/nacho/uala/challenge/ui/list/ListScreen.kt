package com.nacho.uala.challenge.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.nacho.uala.challenge.R.string.error
import com.nacho.uala.challenge.R.string.no_results
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.ui.list.components.CitySearchBar
import com.nacho.uala.challenge.ui.list.components.LandscapeListAndMap
import com.nacho.uala.challenge.ui.list.components.PortraitCityList

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
    navigateMap: (City) -> Unit
) {
    val selectedCity by viewModel.selectedCity.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val cities = viewModel.cities.collectAsLazyPagingItems()

    val hasItems = cities.itemCount > 0
    val isLoading = cities.loadState.refresh is LoadState.Loading
    val isError = cities.loadState.refresh is LoadState.Error

    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            LisSearchBar(
                query = query,
                onQueryChange = { query ->
                    viewModel.onSearchQueryChanged(query)
                }
            )
        }
    ) { paddingValues ->
        when {
            hasItems -> {
                ListContent(
                    modifier = Modifier.padding(paddingValues),
                    listState = listState,
                    cities = cities,
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

            isError -> {
                ListError(modifier = Modifier.padding(paddingValues))
            }

            isLoading -> {
                ListLoading(modifier = Modifier.padding(paddingValues))
            }

            else -> {
                ListEmpty(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun LisSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit
) {
    CitySearchBar(
        modifier = modifier.padding(8.dp),
        query = query,
        onQueryChange = onQueryChange
    )
}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    cities: LazyPagingItems<CityUiState>,
    selectedCity: City?,
    navigateMap: (City) -> Unit,
    onCityClick: (City) -> Unit,
    onToggleCityFavorite: (CityUiState) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeListAndMap(
            modifier = modifier.fillMaxSize(),
            listState = listState,
            cities = cities,
            selectedCity = selectedCity,
            onCityClick = { city ->
                onCityClick(city)
            },
            onCityToggleFavorite = onToggleCityFavorite
        )
    } else {
        PortraitCityList(
            modifier = modifier
                .fillMaxSize()
                .testTag("city_list_container"),
            listState = listState,
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
            text = stringResource(error),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun ListEmpty(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(no_results),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
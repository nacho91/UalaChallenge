package com.nacho.uala.challenge.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.nacho.uala.challenge.R
import com.nacho.uala.challenge.R.string.error
import com.nacho.uala.challenge.R.string.map_title
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.ui.map.components.CityMap

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    cityId: Int,
    onBackClick: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    LaunchedEffect(cityId) {
        viewModel.loadCity(cityId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MapToolBar(
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        val state by viewModel.uiState.collectAsState()

        when {
            state.isError -> {
                MapError(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            state.city != null -> {
                MapContent(
                    modifier = Modifier.padding(paddingValues),
                    city = state.city!!
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapToolBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(map_title))
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        }
    )
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
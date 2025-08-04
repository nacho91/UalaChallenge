package com.nacho.uala.challenge.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nacho.uala.challenge.R
import com.nacho.uala.challenge.R.string.detail_title
import com.nacho.uala.challenge.R.string.error
import com.nacho.uala.challenge.domain.model.City

@Composable
fun CityDetailScreen(
    modifier: Modifier = Modifier,
    cityId: Int,
    onBackClick: () -> Unit,
    viewModel: CityDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(cityId) {
        viewModel.loadCity(cityId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DetailToolBar(
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        val state by viewModel.uiState.collectAsState()

        when {
            state.isError -> {
                DetailError(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            state.city != null -> {
                DetailContent(
                    modifier = Modifier.padding(paddingValues),
                    city = state.city!!
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailToolBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(detail_title))
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        }
    )
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    city: City
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text =stringResource(R.string.detail_city, city.name),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(R.string.detail_country, city.country),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(R.string.detail_latitude, city.lat),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.detail_longitude, city.lon),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DetailError(
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
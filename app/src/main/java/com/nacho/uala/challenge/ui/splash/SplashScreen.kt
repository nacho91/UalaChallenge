package com.nacho.uala.challenge.ui.splash

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nacho.uala.challenge.R
import com.nacho.uala.challenge.R.string.error
import com.nacho.uala.challenge.R.string.initializing
import com.nacho.uala.challenge.R.string.retry_button

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateList: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                Loading()
            }
            state.isError -> {
                Error(
                    onRetry = {
                        viewModel.initializeCities()
                    }
                )
            }
            state.isSuccess -> {
                LaunchedEffect(Unit) {
                    navigateList()
                }
            }
        }
    }
}

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(initializing),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun Error(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(error),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(retry_button))
        }
    }
}
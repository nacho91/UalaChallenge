package com.nacho.uala.challenge.ui.map.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.nacho.uala.challenge.R.string.map_marker_title
import com.nacho.uala.challenge.R.string.map_marker_snippet
import com.nacho.uala.challenge.domain.model.City

@Composable
fun CityMap(
    modifier: Modifier = Modifier,
    city: City
) {
    val latLng = LatLng(city.lat, city.lon)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }

    val markerState = remember(city) {
        MarkerState(position = latLng)
    }

    LaunchedEffect(city) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(latLng, 15f),
            durationMs = 1000
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = markerState,
            title = stringResource(map_marker_title, city.name, city.country),
            snippet = stringResource(map_marker_snippet, city.lat, city.lon)
        )
    }
}
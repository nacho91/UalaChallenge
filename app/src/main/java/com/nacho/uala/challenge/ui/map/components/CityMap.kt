package com.nacho.uala.challenge.ui.map.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.nacho.uala.challenge.domain.model.City

@Composable
fun CityMap(
    modifier: Modifier = Modifier,
    city: City?
) {
    val markerState = remember(city) {
        if (city != null) {
            MarkerState(position = LatLng(city.lat, city.lon))
        } else {
            null
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            if (city != null) {
                position = CameraPosition.fromLatLngZoom(
                    LatLng(city.lat, city.lon),
                    10f
                )
            }
        }
    ) {
        if (city != null && markerState != null) {
            Marker(
                state = markerState,
                title = city.name
            )
        }
    }
}
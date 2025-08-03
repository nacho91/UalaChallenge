package com.nacho.uala.challenge.ui.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nacho.uala.challenge.R
import com.nacho.uala.challenge.R.string.city_title
import com.nacho.uala.challenge.R.string.city_coordinates
import com.nacho.uala.challenge.R.string.city_favorite_button_desc
import com.nacho.uala.challenge.domain.model.City

@Composable
fun CityList(
    modifier: Modifier = Modifier,
    cities: List<City>,
    onClick: (City) -> Unit,
    onToggleFavorite: (City) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(cities) { city ->
            CityItem(
                modifier = Modifier.testTag("city_item"),
                city = city,
                onClick = {
                    onClick(city)
                },
                onToggleFavorite = {
                    onToggleFavorite(city)
                }
            )
        }
    }
}

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    city: City,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ){
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
        IconButton(
            modifier = Modifier.testTag("favorite_button"),
            onClick = { onToggleFavorite() }) {
            Icon(
                imageVector = if (city.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(city_favorite_button_desc),
                tint = if (city.isFavorite) Color.Red else LocalContentColor.current
            )
        }
    }
}

@Preview
@Composable
fun CityListPreview() {
    val cities = listOf(
        City(
            id = 0,
            country = "AR",
            name = "Argentina",
            lat = 0.0,
            lon = 0.0,
            isFavorite = false
        ),
        City(
            id = 0,
            country = "UY",
            name = "Uruguay",
            lat = 0.0,
            lon = 0.0,
            isFavorite = true
        )
    )
    CityList(
        modifier = Modifier.background(Color.White),
        cities = cities,
        onClick = {},
        onToggleFavorite = {}
    )
}
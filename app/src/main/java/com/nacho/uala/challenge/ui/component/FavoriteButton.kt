package com.nacho.uala.challenge.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    contentDescription: String,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onToggleFavorite()
        }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = contentDescription,
            tint = if (isFavorite) Color.Red else LocalContentColor.current
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    var isFavorite by remember { mutableStateOf(false) }

    FavoriteButton(
        contentDescription = "",
        isFavorite = isFavorite,
        onToggleFavorite = {
            isFavorite = !isFavorite
        }
    )
}
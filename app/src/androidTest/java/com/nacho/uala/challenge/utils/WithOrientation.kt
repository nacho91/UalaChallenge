package com.nacho.uala.challenge.utils

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun WithOrientation(
    orientation: Int,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val resources = context.resources
    val config = Configuration(resources.configuration).apply {
        this.orientation = orientation
    }

    CompositionLocalProvider(
        LocalConfiguration provides config,
        content = content
    )
}
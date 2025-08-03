package com.nacho.uala.challenge.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nacho.uala.challenge.ui.list.ListScreen
import com.nacho.uala.challenge.ui.splash.SplashScreen

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        Scaffold { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = "splash"
            ) {
                composable("splash") {
                    SplashScreen(
                        navController = navController
                    )
                }

                composable("list") {
                    ListScreen(
                        onCityClick = { city ->
                            // TODO navigate to map
                        }
                    )
                }
            }
        }
    }
}
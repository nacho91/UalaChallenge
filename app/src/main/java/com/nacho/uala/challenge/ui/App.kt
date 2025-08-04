package com.nacho.uala.challenge.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nacho.uala.challenge.ui.list.ListScreen
import com.nacho.uala.challenge.ui.map.MapScreen
import com.nacho.uala.challenge.ui.splash.SplashScreen

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
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
                    navigateMap = { city ->
                        navController.navigate("map/${city.id}")
                    }
                )
            }

            composable(
                "map/{cityId}",
                arguments = listOf(navArgument("cityId") { type = NavType.IntType })
            ) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getInt("cityId") ?: return@composable

                MapScreen(
                    cityId = cityId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
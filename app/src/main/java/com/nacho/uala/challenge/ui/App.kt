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
import com.nacho.uala.challenge.ui.detail.CityDetailScreen
import com.nacho.uala.challenge.ui.list.ListScreen
import com.nacho.uala.challenge.ui.map.MapScreen
import com.nacho.uala.challenge.ui.splash.SplashScreen

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppDestination.Splash.route
        ) {
            composable(AppDestination.Splash.route) {
                SplashScreen(
                    navigateList = {
                        navController.navigate(AppDestination.List.route) {
                            popUpTo(AppDestination.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppDestination.List.route) {
                ListScreen(
                    navigateMap = { city ->
                        navController.navigate(AppDestination.Map.createRoute(city.id))
                    },
                    navigateDetail = { city ->
                        navController.navigate(AppDestination.Detail.createRoute(city.id))
                    }
                )
            }

            composable(
                AppDestination.Map.route,
                arguments = listOf(navArgument("cityId") { type = NavType.IntType })
            ) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getInt("cityId") ?: return@composable

                MapScreen(
                    cityId = cityId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                AppDestination.Detail.route,
                arguments = listOf(navArgument("cityId") { type = NavType.IntType })
            ) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getInt("cityId") ?: return@composable

                CityDetailScreen(
                    cityId = cityId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
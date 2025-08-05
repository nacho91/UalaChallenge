package com.nacho.uala.challenge.ui

sealed class AppDestination(val route: String) {
    object Splash : AppDestination("splash")
    object List : AppDestination("list")

    object Map : AppDestination("map/{cityId}") {
        fun createRoute(cityId: Int) = "map/$cityId"
    }

    object Detail : AppDestination("detail/{cityId}") {
        fun createRoute(cityId: Int) = "detail/$cityId"
    }
}
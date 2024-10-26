package com.jphill.mbtadepatureboard

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jphill.mbtadepatureboard.screens.home.HomeScreen
import com.jphill.mbtadepatureboard.screens.routes.RoutesScreen
import com.jphill.mbtadepatureboard.screens.stops.StopsScreen
import kotlinx.serialization.Serializable

@Serializable
data object Home
@Serializable
data class Routes(val types: List<Int>)
@Serializable
data class Stops(val routeId: String)

@Composable
fun MBTA() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onRouteSelected = { types ->
                    navController.navigate(Routes(types.map { it.toNetwork() }))
                }
            )
        }
        composable<Routes> {
            val routes = it.toRoute<Routes>()
            RoutesScreen(
                types = routes.types,
                onRouteClick = { routeId ->
                    navController.navigate(Stops(routeId))
                }
            )
        }
        composable<Stops> {
            val stops = it.toRoute<Stops>()
            StopsScreen(
                routeId = stops.routeId
            )
        }
    }
}

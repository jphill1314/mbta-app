package com.jphill.mbtadepatureboard

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jphill.mbtadepatureboard.screens.home.HomeScreen
import com.jphill.mbtadepatureboard.screens.routes.RoutesScreen
import kotlinx.serialization.Serializable

@Serializable
data object Home
@Serializable
data class Routes(val types: List<Int>)

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
                viewModel = hiltViewModel(),
                types = routes.types,
            )
        }
    }
}

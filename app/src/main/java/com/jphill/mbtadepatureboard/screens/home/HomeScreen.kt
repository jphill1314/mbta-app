package com.jphill.mbtadepatureboard.screens.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jphill.mbtadepatureboard.data.Route
import com.jphill.mbtadepatureboard.data.Stop
import com.jphill.mbtadepatureboard.fetchUserLocation
import com.jphill.mbtadepatureboard.ui.theme.MBTAScreen

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onRouteSelected: (List<Route.Type>) -> Unit,
) {
    val viewState by homeViewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        fetchUserLocation(
            context = context,
            callbacks = homeViewModel,
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        if (
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
        ) {
            fetchUserLocation(
                context = context,
                callbacks = homeViewModel,
            )
        }
    }

    MBTAScreen {
        Column {
            Text(
                text = "Nearby Stops",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            when {
                viewState.showRequestPermissionItem -> LocationPermissionRequest {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                        )
                    )
                }
                viewState.isNearbyLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> NearbyStopGrid(
                    stops = viewState.nearbyStops,
                    modifier = Modifier.fillMaxWidth(),
                    onStopClick = {},
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Select stop by line",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            RouteTypeSelection(
                text = "Subway",
                onClick = {
                    onRouteSelected(
                        listOf(
                            Route.Type.HEAVY_RAIL,
                            Route.Type.LIGHT_RAIL,
                        )
                    )
                },
            )

            RouteTypeSelection(
                text = "Commuter Rail",
                onClick = {
                    onRouteSelected(listOf(Route.Type.COMMUTER_RAIL))
                },
            )

            RouteTypeSelection(
                text = "Bus",
                onClick = {
                    onRouteSelected(listOf(Route.Type.BUS))
                },
            )

            RouteTypeSelection(
                text = "Ferry",
                onClick = {
                    onRouteSelected(listOf(Route.Type.FERRY))
                },
            )
        }
    }
}

@Composable
private fun RouteTypeSelection(
    text: String,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(text = text)
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun LocationPermissionRequest(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
    ) {
        Text(
            text = "Enable location permission to see nearby stops",
        )
    }
}

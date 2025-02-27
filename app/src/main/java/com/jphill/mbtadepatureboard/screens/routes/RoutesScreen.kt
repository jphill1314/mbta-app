package com.jphill.mbtadepatureboard.screens.routes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jphill.mbtadepatureboard.data.Route
import com.jphill.mbtadepatureboard.ui.theme.MBTAScreen

@Composable
fun RoutesScreen(
    types: List<Int>,
    onRouteClick: (String) -> Unit,
    viewModel: RoutesViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(types) { viewModel.fetchRoutes(types) }

    MBTAScreen {
        LazyColumn {
            items(viewState.routes) { route ->
                RouteItem(
                    route = route,
                    onClick = { onRouteClick(route.id) },
                )
            }
        }
    }
}

@Composable
private fun RouteItem(
    route: Route,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(
                text = route.name,
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}


package com.jphill.mbtadepatureboard.screens.stops

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
import com.jphill.mbtadepatureboard.Predictions
import com.jphill.mbtadepatureboard.data.Stop
import com.jphill.mbtadepatureboard.ui.theme.MBTAScreen

@Composable
fun StopsScreen(
    routeId: String,
    onStopSelected: (Predictions) -> Unit,
    viewModel: StopsViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(routeId) {
        viewModel.fetchStopsForRoute(routeId)
    }

    MBTAScreen {
        LazyColumn {
            items(viewState.stops) { stop ->
                StopListItem(stop) {
                    onStopSelected(
                        Predictions(
                            routeId = routeId,
                            stopId = stop.id,
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun StopListItem(
    stop: Stop,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(
                text = stop.attributes.name,
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}
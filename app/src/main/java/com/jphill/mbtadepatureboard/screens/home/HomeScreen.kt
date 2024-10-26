package com.jphill.mbtadepatureboard.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jphill.mbtadepatureboard.data.Route
import com.jphill.mbtadepatureboard.ui.theme.MBTAScreen

@Composable
fun HomeScreen(
    onRouteSelected: (List<Route.Type>) -> Unit,
) {
    MBTAScreen {
        Column {
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
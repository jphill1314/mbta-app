package com.jphill.mbtadepatureboard.screens.predictions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jphill.mbtadepatureboard.ui.theme.MBTAScreen
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.Date

data class PredictionViewItem(
    val arrivalTime: LocalDateTime?,
    val departureTime: LocalDateTime?,
    val direction: String,
)

data class PredictionByDirection(
    val direction: String,
    val predictions: List<PredictionViewItem>,
)

@Composable
fun PredictionsScreen(
    stopId: String,
    routeId: String,
    viewModel: PredictionsViewModel = hiltViewModel(),
) {
    viewModel.fetchPredictionsForStopAndRoute(stopId, routeId)
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    MBTAScreen {
        LazyColumn (modifier = Modifier.padding(16.dp)) {
            items(viewState.predictions) { predictionByDirection ->
                PredictionGroup(viewState.currentTime, predictionByDirection)
            }
        }
    }
}

@Composable
private fun PredictionGroup(
    currentTime: LocalDateTime,
    predictionsByDirection: PredictionByDirection,
) {
    Column {
        Text(
            text = predictionsByDirection.direction,
            style = MaterialTheme.typography.displayMedium,
        )

        predictionsByDirection.predictions.forEach { predictions ->
            val difference = ChronoUnit.MINUTES.between(currentTime, predictions.arrivalTime)
            Text(
                text = "Arriving in: $difference minutes"
            )
        }
    }
}
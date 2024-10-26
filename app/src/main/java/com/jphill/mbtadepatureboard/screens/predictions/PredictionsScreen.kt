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


@Composable
fun PredictionsScreen(
    stopId: String,
    routeId: String,
    viewModel: PredictionsViewModel = hiltViewModel(),
) {
    viewModel.fetchPredictionsForStopAndRoute(stopId, routeId)
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    MBTAScreen {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(viewState.predictionSections) { predictionSection ->
                PredictionSection(predictionSection)
            }
        }
    }
}

@Composable
private fun PredictionSection(
    section: PredictionSection,
) {
    Column {
        Text(
            text = section.headerTitle,
            style = MaterialTheme.typography.displaySmall,
        )

        section.predictions.forEach { prediction ->
            Text(text = prediction.displayText)
        }
    }
}
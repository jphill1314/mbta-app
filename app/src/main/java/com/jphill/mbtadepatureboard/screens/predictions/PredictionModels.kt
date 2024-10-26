package com.jphill.mbtadepatureboard.screens.predictions

import java.time.LocalDateTime

data class PredictionsDataState(
    val currentTime: LocalDateTime = LocalDateTime.now(),
    val predictions: List<PredictionByDirection> = emptyList(),
)

data class PredictionData(
    val arrivalTime: LocalDateTime?,
    val departureTime: LocalDateTime?,
    val direction: String,
)

data class PredictionByDirection(
    val direction: String,
    val predictions: List<PredictionData>,
)

data class PredictionSection(
    val headerTitle: String,
    val predictions: List<PredictionItem>,
)

data class PredictionItem(
    val displayText: String,
)

data class PredictionsViewState(
    val predictionSections: List<PredictionSection>,
)
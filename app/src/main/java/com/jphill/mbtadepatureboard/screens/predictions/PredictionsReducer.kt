package com.jphill.mbtadepatureboard.screens.predictions

import com.jphill.mbtadepatureboard.common.Reducer
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object PredictionsReducer : Reducer<PredictionsViewState, PredictionsDataState> {

    override fun reduce(dataState: PredictionsDataState): PredictionsViewState {
        return if (dataState.predictions.isEmpty()) {
            val noPredictionsSection = PredictionSection(
                headerTitle = "No arrival data available",
                predictions = emptyList()
            )
            PredictionsViewState(predictionSections = listOf(noPredictionsSection))
        } else {
            val sections = dataState.predictions.map { direction ->
                createPredictionSectionForDirection(dataState.currentTime, direction)
            }
            PredictionsViewState(
                predictionSections = sections,
            )
        }
    }

    private fun createPredictionSectionForDirection(
        currentTime: LocalDateTime,
        direction: PredictionByDirection
    ): PredictionSection {
        val basePredictions = direction.predictions.mapNotNull {
            getDisplayTextForPrediction(currentTime, it)?.let { text ->
                PredictionItem(text)
            }
        }
        val predictions = basePredictions.ifEmpty {
            listOf(
                PredictionItem(displayText = "No upcoming arrivals")
            )
        }

        return PredictionSection(
            headerTitle = direction.direction,
            predictions = predictions
        )
    }

    private fun getDisplayTextForPrediction(
        currentTime: LocalDateTime,
        prediction: PredictionData,
    ): String? {
        val arrivalDifference = prediction.arrivalTime?.let {
            ChronoUnit.MINUTES.between(currentTime, it)
        } ?: -1
        return if (arrivalDifference <= 0) {
            val departureDifference = prediction.departureTime?.let {
                ChronoUnit.MINUTES.between(currentTime, it)
            } ?: -1
            when {
                departureDifference == 0L -> "Departing now"
                departureDifference > 0L -> "Departing in $departureDifference minutes"
                else -> null
            }
        } else {
            "Arriving in $arrivalDifference minutes"
        }
    }
}